package com.test.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empleados") // Unificación de url
public class EmpleadoControlador {

    @Autowired
    private EmpleadosRepository empleadosRepository;

    @GetMapping
    public Iterable<Empleados> getEmpleadoById(){
        return empleadosRepository.findAll();
    }

    @GetMapping("/{idEmpleado}")
    public ResponseEntity<?> getEmpleadoById(@PathVariable Long idEmpleado){
        Optional<Empleados> empleados = empleadosRepository.findById(idEmpleado);
        if (empleados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Empleados empleadoObj = empleados.get();
        return ResponseEntity.ok(empleadoObj);
    }

    @PostMapping
    public ResponseEntity<?> postEmpleado(@RequestBody EmpleadoDTO empleadoDTO){
        Empleados empleados = new Empleados();
        empleados.setNombre(empleadoDTO.nombre());
        empleadosRepository.save(empleados);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{idEmpleado}")
    public ResponseEntity<?> putEmpleado(@PathVariable Long idEmpleado, @RequestBody EmpleadoDTO empleadoDTO){
        Optional<Empleados> empleadosOptional = empleadosRepository.findById(idEmpleado);
        if (empleadosOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Empleados empleadoObj = empleadosOptional.get();
        empleadoObj.setNombre(empleadoDTO.nombre());
        empleadosRepository.save(empleadoObj);
        return ResponseEntity.ok().build();

    }
    @DeleteMapping("/{idEmpleado}")
    public ResponseEntity<?> deleteEmpleado(@PathVariable Long idEmpleado){
        empleadosRepository.deleteById(idEmpleado);
        return ResponseEntity.ok().build();
    }
}
