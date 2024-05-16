package demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class H2DemoService implements DemoService {
	private DemoDao demoDao;

	public H2DemoService(DemoDao demoDao) {
		this.demoDao = demoDao;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<DemoBoundary> getSpecificDemo(String id) {
		Optional<DemoEntity> entityOp = this.demoDao.findById(id);
		Optional<DemoBoundary> boundaryOp = entityOp.map(this::toBoundary);
		
		return boundaryOp;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DemoBoundary> getAllDemoes() {
		List<DemoEntity> entities = this.demoDao.findAll();
		List<DemoBoundary> boundaries = new ArrayList<>();
		for (DemoEntity entity : entities) {
			boundaries.add(this.toBoundary(entity));
		}
		return boundaries;
	}

	@Override
	@Transactional(readOnly = false)
	public DemoBoundary createDemo(DemoBoundary demoBoundary) {
		demoBoundary.setId(UUID.randomUUID().toString());
		demoBoundary.setMessageTimestamp(new Date());
		
		DemoEntity entity = this.toEntity(demoBoundary);
		entity = this.demoDao.save(entity);
		
		return this.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAllDemoes() {
		this.demoDao.deleteAll();
	}

	@Override
	@Transactional(readOnly = false)
	public void updateDemo(String id, DemoBoundary update) {
		// TODO implement this operation later
		System.err.println("*** not implemented yet");
		throw new RuntimeException("not implemented yet");
	}

	private DemoBoundary toBoundary(DemoEntity entity) {
		DemoBoundary boundary = new DemoBoundary();
		
		boundary.setId(entity.getId());
		boundary.setMessage(entity.getMessage());
		
		return boundary;
	}

	private DemoEntity toEntity (DemoBoundary boundary) {
		DemoEntity entity = new DemoEntity();
		
		entity.setId(boundary.getId());
		entity.setMessage(boundary.getMessage());
		
		return entity;
	}
	
}
