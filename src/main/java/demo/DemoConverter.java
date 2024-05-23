package demo;

import org.springframework.stereotype.Component;

@Component
public class DemoConverter {
	public DemoBoundary toBoundary(DemoEntity entity) {
		DemoBoundary boundary = new DemoBoundary();
		
		boundary.setId(entity.getId());
		boundary.setMessage(entity.getMessage());
		boundary.setMessageTimestamp(entity.getMessageTimestamp());
		boundary.setDetails(entity.getDetails());
		boundary.setStatus(
				toBoundary(entity.getStatus()));
		boundary.setVersion(entity.getVersion());
		
		boundary.setName(new NameBoundary(
				entity.getFirstName(),
				entity.getLastName()));
		
		return boundary;
	}

	public DemoEntity toEntity (DemoBoundary boundary) {
		DemoEntity entity = new DemoEntity();
		
		entity.setId(boundary.getId());
		entity.setMessage(boundary.getMessage());
		entity.setMessageTimestamp(boundary.getMessageTimestamp());
		entity.setDetails(boundary.getDetails());
		entity.setStatus(this.toEntity(boundary.getStatus()));
		if (boundary.getVersion() != null) {
			entity.setVersion(boundary.getVersion());
		}else {
			entity.setVersion(0.0);
		}
		
		if (boundary.getName() != null) {
			entity.setFirstName(boundary.getName().getFirstName());
			entity.setLastName(boundary.getName().getLastName());
		}else {
			entity.setFirstName(null);
			entity.setLastName(null);
		}
		
		return entity;
	}

	public StatusEnum toBoundary(StatusEnumInDB status) {
		return StatusEnum.valueOf(status.name().toUpperCase());
	}

	public StatusEnumInDB toEntity(StatusEnum status) {
		return StatusEnumInDB.valueOf(status.name().toLowerCase());
	}
}
