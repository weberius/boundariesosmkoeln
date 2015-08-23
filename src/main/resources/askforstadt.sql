select 
  osm_id, 
  admin_level, 
  name, 
  ref, 
  -- hier die aufloesung definiert, 1: sehr genau, 10: weniger genau, 100: ungenau
  ST_ASGEOJSON(ST_Transform(ST_Simplify(way,?),4326)) as way
from 
  planet_osm_polygon
where 
  boundary = 'administrative' and admin_level = '6'
  and lower(name) like 'köln'
