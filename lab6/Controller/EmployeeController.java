package com.example.lab6.Controller;

import com.example.lab6.Model.Employee;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    ArrayList<Employee> employees = new ArrayList<>();

    @GetMapping("/get")
    public ResponseEntity getEmployees(){
        return ResponseEntity.status(200).body(employees);
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        }
        employees.add(employee);
        return ResponseEntity.status(200).body("Employee added successfully");
    }

    @PutMapping("/update")
    public ResponseEntity updateEmployee(@RequestParam String id, @RequestBody @Valid Employee employee,Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getFieldError().getDefaultMessage());
        }
            for (int i=0;i<employees.size();i++){
                if (employees.get(i).getID().equals(id)){
                    employees.set(i,employee);
                    return ResponseEntity.status(200).body("Employee updated successfully");
                }
            }
        return ResponseEntity.status(200).body("Employee Not Found");
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteEmployee(@RequestParam String id){
        for (Employee e:employees){
            if(e.getID().equals(id)){
                employees.remove(e);
                return ResponseEntity.status(200).body("Employee deleted successfully");
            }
        }
        return ResponseEntity.status(200).body("Employee Not Found");
    }

    @GetMapping("/search-by-position")
    public ResponseEntity searchByPosition(@RequestParam @Pattern(regexp = "^(supervisor|coordinator)$", message = "Position must be either 'supervisor' or 'coordinator'") String position){
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
            for (Employee e : employees){
                if (e.getPosition().equals(position)) employeeArrayList.add(e);
            }
            return ResponseEntity.status(200).body(employeeArrayList);
    }

    @GetMapping("/get-by-age")
    public ResponseEntity getEmployeesByAgeRange(@RequestParam @Min(25) int min, @RequestParam int max){
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        for (Employee e : employees){
            if (e.getAge()>=min && e.getAge()<=max) employeeArrayList.add(e);
        }
        return ResponseEntity.status(200).body(employeeArrayList);
    }

    @PutMapping("/annual-leave")
    public ResponseEntity applyForAnnualLeave(@RequestParam String id){
        for (Employee e:employees){
            if (e.getID().equals(id) && !e.isOnLeave() && e.getAnnualLeave()>0){
                e.setOnLeave(true);
                e.setAnnualLeave(e.getAnnualLeave()-1);
                return ResponseEntity.status(200).body("The Application has been done");
            }
        }
        return ResponseEntity.status(200).body("You can't apply for annual leave");
    }

    @GetMapping("/no-annual-leave")
    public ResponseEntity getEmployeesWithNoAnnualLeave(){
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        for (Employee e:employees){
            if (e.getAnnualLeave()<1) employeeArrayList.add(e);
        }
        return ResponseEntity.status(200).body(employeeArrayList);
    }

    @PutMapping("/promotion")
    public ResponseEntity promoteEmployee(@RequestParam String id,@RequestParam String id2){
        for (Employee e:employees){
            if (e.getID().equals(id) && e.getPosition().equals("supervisor")) {
                for(Employee e1:employees){
                    if (e1.getID().equals(id2) && e.getAge() >= 30 && !e1.isOnLeave()) {
                        e1.setPosition("supervisor");
                        return ResponseEntity.status(200).body("The employee has been promoted");
                    }
                }
            }
        }
        return ResponseEntity.status(200).body("The employee can't be promoted");
    }


}
