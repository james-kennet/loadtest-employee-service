package com.employeeservice.databases.mongodb.dao.document;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "employees")
public class MongoEmployee {

  @Id private String id;
  private String firstName;
  private String lastName;
  private int age;
  private String email;
  private String occupation;
}
