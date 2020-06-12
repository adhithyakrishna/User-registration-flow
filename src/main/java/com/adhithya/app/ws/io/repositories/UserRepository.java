 package com.adhithya.app.ws.io.repositories;
 
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.adhithya.app.ws.io.entity.UserEntity;

// this interface is created to take a user entity class and persist the data into the database
// crudrepository is an shortcut to overcome creating dao and it is provided by JPA 
// or methods that use hibernates this is prevented by datajpa

//jpa makes it easier, instead of dao we create user respositories that extends crudrespositiories that
// provides ready to use methods
// CrudRepository is generic
// first param is the class of the object that needs to be persisted
// datatype of id field 

//this by default takes care of the crus operations

//in addition to the functions present in the crudrespository we can make use of our own business logic within this class 
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> 
{
	// the method name should be in accordance to the pattern, the fieldname should be in capitals and we should make use of By
	UserEntity findByEmail(String email);

	UserEntity findByUserId(String userId);
}