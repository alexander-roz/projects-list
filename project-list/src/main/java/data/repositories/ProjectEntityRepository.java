package data.repositories;

import data.entities.ProjectEntity;

public interface ProjectEntityRepository{
    ProjectEntity findByName(String name);

}
