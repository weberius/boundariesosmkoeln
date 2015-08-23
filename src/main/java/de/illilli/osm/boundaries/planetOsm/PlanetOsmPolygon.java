package de.illilli.osm.boundaries.planetOsm;


public class PlanetOsmPolygon {

	private long osm_id;
	private String name;
	private String admin_level;
	private String ref;
	private String way;
	private String imageFile;
	private static String escape = "\"";

	public String getName() {
		if (name != null && name.contains(escape)) {
			return this.name.replace(escape, "");
		} else {
			return this.name;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public long getOsm_id() {
		return osm_id;
	}

	public void setOsm_id(long osm_id) {
		this.osm_id = osm_id;
	}

	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getAdmin_level() {
		return admin_level;
	}

	public void setAdmin_level(String admin_level) {
		this.admin_level = admin_level;
	}

	@Override
	public String toString() {
		return "PlanetOsmPoint [osm_id=" + osm_id + ", name=" + name
				+ ", admin_level=" + admin_level + ", ref=" + ref + ", way="
				+ way + ", imageFile=" + imageFile + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((admin_level == null) ? 0 : admin_level.hashCode());
		result = prime * result
				+ ((imageFile == null) ? 0 : imageFile.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (osm_id ^ (osm_id >>> 32));
		result = prime * result + ((ref == null) ? 0 : ref.hashCode());
		result = prime * result + ((way == null) ? 0 : way.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PlanetOsmPolygon other = (PlanetOsmPolygon) obj;
		if (admin_level == null) {
			if (other.admin_level != null) {
				return false;
			}
		} else if (!admin_level.equals(other.admin_level)) {
			return false;
		}
		if (imageFile == null) {
			if (other.imageFile != null) {
				return false;
			}
		} else if (!imageFile.equals(other.imageFile)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (osm_id != other.osm_id) {
			return false;
		}
		if (ref == null) {
			if (other.ref != null) {
				return false;
			}
		} else if (!ref.equals(other.ref)) {
			return false;
		}
		if (way == null) {
			if (other.way != null) {
				return false;
			}
		} else if (!way.equals(other.way)) {
			return false;
		}
		return true;
	}

}
