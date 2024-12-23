package org.ysikorsky.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.List;


@Component
public class TaskStorage {

	private final StorageTaskRepository storageTaskRepository;

	@Autowired
	public TaskStorage(StorageTaskRepository storageTaskRepository) {
		this.storageTaskRepository = storageTaskRepository;
	}

	//____________________________ TASKS TABLE

	public List<StorageTask> firstCreatedTask() {
		return storageTaskRepository.firstCreatedTask();
	}

	public void updateTaskInProgress(StorageTask storageTask) {
		int x = storageTaskRepository.getCounterProcessor(storageTask.getId());
				storageTaskRepository.saveInProgressData(
				storageTask.getState().toString(),
				Timestamp.valueOf(LocalDateTime.now()),
				x+1,
				storageTask.getVersion(),
				storageTask.getId()
				);
	}

	public void updateTaskDone(StorageTask storageTask) {
		storageTaskRepository.saveDoneData(
				storageTask.getAnswer(),
				storageTask.getState().toString(),
				Timestamp.valueOf(storageTask.getLocalDateTimeDone()),
				storageTask.getVersion()+1,
				storageTask.getId()
		);
	}

	//____________________________ PROCESSOR_SPEED TABLE

	public void increaseProcessorSpeed(int id, int processorSpeed) {
		storageTaskRepository.updateProcessorSpeed(id, processorSpeed);
	}

	public void decreaseProcessorSpeed(int id, int processorSpeed) {
		storageTaskRepository.updateProcessorSpeed(id, processorSpeed);
	}

	public int setProcessorSpeed(int id, int processorSpeed) {
		storageTaskRepository.saveProcessorSpeed(id, processorSpeed);
		Integer speed = storageTaskRepository.getProcessorSpeed(id);
		if (speed == null) {
			speed = 1;
		}
		return speed;
	}

	public int getProcessorSpeed(int id) {
		Integer speed = storageTaskRepository.getProcessorSpeed(1);
		if (speed == null) {
			speed = 1;
		}
		return speed;
	}

	public void truncateProcessorSpeed() {
		storageTaskRepository.truncateProcessorSpeed();
	}

}