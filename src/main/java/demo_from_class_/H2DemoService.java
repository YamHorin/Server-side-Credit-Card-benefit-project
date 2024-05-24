package demo_from_class_;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class H2DemoService implements DemoService {
	private DemoDao demoDao;
	private DemoConverter demoConverter;
	private String defaultFirstName;
	
	public H2DemoService(
			DemoDao demoDao,
			DemoConverter demoConverter) {
		this.demoDao = demoDao;
		this.demoConverter = demoConverter;
	}

	@Value("${dummy.defaultFirstName:Jill}")
	public void setDefaultFirstName(String defaultFirstName) {
		System.err.println("**** reading from configuration default first name: " + defaultFirstName);
		this.defaultFirstName = defaultFirstName;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<DemoBoundary> getSpecificDemo(String id) {
		Optional<DemoEntity> entityOp = this.demoDao.findById(id); //Optional<DemoEntity>
		Optional<DemoBoundary> boundaryOp = entityOp
				.map(this.demoConverter::toBoundary); //Optional<DemoBoundary>
		
		if (boundaryOp.isEmpty()) {
			System.err.println("* no demo to return");
		}else {
			System.err.println("* " + boundaryOp.get());
		}
		return boundaryOp;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DemoBoundary> getAllDemoes() {
		List<DemoEntity> entities = this.demoDao.findAll();
		List<DemoBoundary> boundaries = new ArrayList<>();
		for (DemoEntity entity : entities) {
			boundaries.add(this.demoConverter.toBoundary(entity));
		}
		
		System.err.println("* data from database: " + boundaries);
		return boundaries;
	}

	@Override
	@Transactional(readOnly = false)
	public DemoBoundary createDemo(DemoBoundary demoBoundary) {
		System.err.println("* client requested to store: " + demoBoundary);
		demoBoundary.setId(UUID.randomUUID().toString());
		demoBoundary.setMessageTimestamp(new Date());
		if (demoBoundary.getVersion() == null) {
			demoBoundary.setVersion(1.0);
		}
		if (demoBoundary.getStatus() == null) {
			demoBoundary.setStatus(StatusEnum.UNDETERMINED);
		}
		
		if (demoBoundary.getName() == null) {
			demoBoundary.setName(new NameBoundary());
		}
		
		if (demoBoundary.getName().getFirstName() == null) {
			demoBoundary.getName().setFirstName(this.defaultFirstName);
		}
		DemoEntity entity = this.demoConverter.toEntity(demoBoundary);
		entity = this.demoDao.save(entity);
		
		DemoBoundary rv =  this.demoConverter.toBoundary(entity);
		
		System.err.println("* server stored: " + rv);

		return rv;
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAllDemoes() {
		System.err.println("* deleting table for demoes");
		this.demoDao.deleteAll();
	}

	@Override
	@Transactional(readOnly = false)
	public void updateDemo(String id, DemoBoundary update) {
		System.err.println("* updating demo with id: " + id + ", with the following details: " + update);
		
		DemoEntity entity = this.demoDao
			.findById(id)
			.orElseThrow(()->new RuntimeException(
				"Could not find demo for update by id: " + id));
		
		// ignore id
		// ignore timestamp
		
		if (update.getMessage() != null) {
			entity.setMessage(update.getMessage());
		}
		
		if (update.getDetails() != null) {
			entity.setDetails(update.getDetails());
		}
		
		if (update.getStatus() != null) {
			entity.setStatus(
				this.demoConverter.toEntity(
					update.getStatus()));
		}
		
		if (update.getVersion() != null) {
			entity.setVersion(update.getVersion());
		}
		
		if (update.getName() != null) {
			if (update.getName().getFirstName() != null) {
				entity.setFirstName(update.getName().getFirstName());
			}
			
			if (update.getName().getLastName() != null) {
				entity.setLastName(update.getName().getLastName());
			}
		}
		
		entity = this.demoDao.save(entity);
		System.err.println("* " + entity);
	}

}
