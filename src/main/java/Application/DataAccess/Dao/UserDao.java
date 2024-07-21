package Application.DataAccess.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import Application.DataAccess.Entities.EntityUser;


public interface UserDao extends JpaRepository<EntityUser, String>{

}
