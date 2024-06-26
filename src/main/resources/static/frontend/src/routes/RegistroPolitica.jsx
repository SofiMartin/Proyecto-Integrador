import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "reactstrap";
import "./RegistroPolitica.css";
import Swal from "sweetalert2";

const RegistroPolitica = () => {
  const [formData, setFormData] = useState({
    nombre: "",
    descripcion: "",
  });

  const handleInputChange = (e) => {
    const { name, value} = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formDataToSend = new FormData();

    formDataToSend.append("nombre", formData.nombre);
    formDataToSend.append("descripcion", formData.descripcion);
    

    let datos = {
      nombre: formData.nombre, 
      descripcion: formData.descripcion,
      
    };

    console.log(formData.nombre);
    console.log(formData.descripcion);
    
    console.log(datos);

    try {
      axios
        .post("http://localhost:8080/politica/registrar", formDataToSend)
        .then((res) => {
          // Manejar la respuesta del servidor, por ejemplo, mostrar un mensaje de éxito
          console.log("Politica registrada exitosamente", res.data);
          Swal.fire({
            icon: "success",
            title: "Exito",
            text: "Registro Exitoso",
            //footer: '<a href="#">Why do I have this issue?</a>'
          });
          //alert("Registro exitoso");

          // Limpiar el formulario o realizar otras acciones necesarias
          // Puedes restablecer los valores del formulario si lo deseas
          setFormData({
            nombre: "",
            descripcion: "",
            
          });
        });
      // Limpiar el formulario o realizar otras acciones necesarias
    } catch (error) {
      console.error("Error al registrar la Politica", error);
    }
  };

  return (
    <div className="registro-container">
    <div className="registro-cabecera">
     <h2 className="registro-politica">Registro de Nueva Politica</h2>
      <Link to="/administrarpoliticas">
          <Button color="secondary" className="--bs-orange">
            Volver
          </Button>
        </Link>
        </div>
      <span />
   
      <form encType="multipart/form-data" onSubmit={handleSubmit}>
        <label htmlFor="nombre">Nombre de la Politica</label>
          <input
            type="text"
            id="nombre"
            name="nombre"
            value={formData.nombre}
            onChange={handleInputChange}
          />
        <label htmlFor="descripcion">Descripcion de la Politica</label>
        <input
          type="text"
          id="descripcion"
          name="descripcion"
          value={formData.descripcion}
          onChange={handleInputChange}
        />
        <input type="submit" value="Guardar" />
      </form>
    </div>
  );
};

export default RegistroPolitica;
