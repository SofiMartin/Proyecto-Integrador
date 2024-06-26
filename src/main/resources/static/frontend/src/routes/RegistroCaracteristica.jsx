import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "reactstrap";
import "./RegistroCaracteristica.css";
import Swal from "sweetalert2";

const RegistroCaracteristica = () => {
  const [formData, setFormData] = useState({
   
    descripcion: "",
    file: "",
  });

  const handleInputChange = (e) => {
    const { name, value, files } = e.target;

    if (name === "file") {
      // Si se selecciona una imagen, se almacena en el estado
      const selectedImage = files[0];
      setFormData({ ...formData, [name]: selectedImage });
    } else {
      // Para otros campos, se almacenan los valores directamente
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formDataToSend = new FormData();

   
    formDataToSend.append("descripcion", formData.descripcion);
    formDataToSend.append("file", formData.file);

    let datos = {
      
      descripcion: formData.descripcion,
      file: formData.file, // Cambiado de un array a un solo archivo
    };

    
    console.log(formData.descripcion);
    console.log(formData.file);
    console.log(datos);

    try {
      axios
        .post("http://localhost:8080/caracteristica/registrar", formDataToSend)
        .then((res) => {
          // Manejar la respuesta del servidor, por ejemplo, mostrar un mensaje de éxito
          console.log("Caracteristica registrada exitosamente", res.data);
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
            
            descripcion: "",
            file: "",
          });
        });
      // Limpiar el formulario o realizar otras acciones necesarias
    } catch (error) {
      console.error("Error al registrar la Caracteristica", error);
    }
  };

  return (
    <div className="registro-container">
    <div className="registro-cabecera">
     <h2 className="registro-caracteristica">Registro de Nueva Caracteristica</h2>
      <Link to="/administrarcaracteristicas">
          <Button color="secondary" className="--bs-orange">
            Volver
          </Button>
        </Link>
        </div>
      <span />
   
      <form encType="multipart/form-data" onSubmit={handleSubmit}>
        
        <label htmlFor="descripcion">Descripcion de la Caracteristica</label>
        <input
          type="text"
          id="descripcion"
          name="descripcion"
          value={formData.descripcion}
          onChange={handleInputChange}
        />

        <label htmlFor="file">Ícono</label>
        <input type="file" id="file" name="file" onChange={handleInputChange} />

        <input type="submit" value="Guardar" />
      </form>
    </div>
  );
};

export default RegistroCaracteristica;
