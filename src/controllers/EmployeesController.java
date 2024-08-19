package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import models.Employees;
import models.EmployeesDao;
import static models.EmployeesDao.rol_user;
import views.SystemView;

public class EmployeesController implements ActionListener {
    private Employees employee;
    private EmployeesDao employeesDao;
    private SystemView views;
        //Rol
    String rol = rol_user;

    public EmployeesController(Employees employee, EmployeesDao employeesDao, SystemView views) {
        this.employee = employee;
        this.employeesDao = employeesDao;
        this.views = views;
        //boton de registrar empleado
        this.views.btn_register_employee.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_employee) {
            //validar que los campos no esten vacios
           if(views.txt_employee_id.getText().equals("")
                   || views.txt_employee_fullname.getText().equals("")
                   || views.txt_employee_username.getText().equals("")
                   ||views.txt_employee_address.getText().equals("")
                   ||views.txt_employee_telephone.getText().equals("")
                   ||views.txt_employee_email.getText().equals("")
                   ||views.cmb_rol.getSelectedItem().toString().equals("")
                   ||String.valueOf(views.txt_employee_password.getPassword()).equals("")){
           JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
           } else {
           //Realizar la inserccion
           employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
           employee.setFull_name(views.txt_employee_fullname.getText().trim());
           employee.setUsername(views.txt_employee_username.getText().trim());
           employee.setAddress(views.txt_employee_address.getText().trim());
           employee.setTelephone(views.txt_employee_telephone.getText().trim());
           employee.setEmail(views.txt_employee_email.getText().trim());
           employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
           employee.setRol(views.cmb_rol.getSelectedItem().toString());
           }
    }
    }
   
    
    
}
