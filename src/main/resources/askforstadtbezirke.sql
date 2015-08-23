--
-- sql from https://trac.osgeo.org/postgis/wiki/UsersWikiSimplifyPreserveTopology
--

with poly as (

select 
  osm_id, 
  admin_level, 
  name, 
  ref, 
  way as geom
from 
  planet_osm_polygon
where 
  -- hier werden die gewuenschten Grenzen definiert; z.B. Stadtteile (admin_level=9 - Stadtteil mit Selbstverwaltung)
  boundary = 'administrative' and admin_level = '9'
  -- hier wird der Rahmen definiert; z.B. admin_level = 6, name = köln
  and ST_CONTAINS((
    select 
      way 
    from 
      planet_osm_polygon
    where 
      boundary = 'administrative' and admin_level = '6'
      and lower(name) like 'köln'
    ), way
  )
) select 
	d.osm_id, 
	d.admin_level, 
	d.name, 
	d.ref, 
	ST_ASGEOJSON(ST_Transform(baz.geom,4326)) as way
  from ( 
        select (st_dump(st_polygonize(distinct geom))).geom as geom
        from (
                select (st_dump(st_simplifyPreserveTopology(st_linemerge(st_union(geom)), ?))).geom as geom
                from (
                        select st_exteriorRing((st_dumpRings(geom)).geom) as geom
                        from poly
                ) as foo
        ) as bar
) as baz,
poly d
where st_intersects(d.geom, baz.geom)
and st_area(st_intersection(d.geom, baz.geom))/st_area(baz.geom) > 0.5
