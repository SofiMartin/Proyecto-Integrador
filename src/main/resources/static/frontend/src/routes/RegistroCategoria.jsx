import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "reactstrap";
import Swal from "sweetalert2";
import "./RegistroCategoria.css";

const RegistroCategoria = () => {
  const [formData, setFormData] = useState({
    nombre: "",
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

    formDataToSend.append("nombre", formData.nombre);
    formDataToSend.append("descripcion", formData.descripcion);
    formDataToSend.append("file", formData.file);

    let datos = {
      nombre: formData.nombre,
      descripcion: formData.descripcion,
      file: formData.file, // Cambiado de un array a un solo archivo
    };

    console.log(formData.nombre);
    console.log(formData.descripcion);
    console.log(formData.file);
    console.log(datos);

    try {
      axios
        .post("http://localhost:8080/categoria/registrar", formDataToSend)
        .then((res) => {
          // Manejar la respuesta del servidor, por ejemplo, mostrar un mensaje de éxito
          console.log("Categoria registrada exitosamente", res.data);
          Swal.fire({
            icon: "success",
            title: "Categoría Registrada",
            text: "La Categoría se ha registrado exitosamente",
          });

          // Limpiar el formulario o realizar otras acciones necesarias
          // Puedes restablecer los valores del formulario si lo deseas
          setFormData({
            nombre: "",
            descripcion: "",
            file: "",
          });
        });
      // Limpiar el formulario o realizar otras acciones necesarias
    } catch (error) {
      console.error("Error al registrar la Categoria", error);
    }
  };

  return (
    <div className="registro-container">
       <div className="registro-cabecera">
     <h2 className="registro-categoria">Registro de Categoría</h2>
      <Link to="/administrador">
          <Button color="secondary" className="--bs-orange">
            Volver
          </Button>
        </Link>
        </div>
        <hr/>
     

      <form encType="multipart/form-data" onSubmit={handleSubmit}>
        <label htmlFor="nombre">Nombre de la Categoría</label>
        <input
          type="text"
          id="nombre"
          name="nombre"
          value={formData.nombre}
          onChange={handleInputChange}
        />

        <label htmlFor="descripcion">Descripcion</label>
        <input
          type="text"
          id="descripcion"
          name="descripcion"
          value={formData.descripcion}
          onChange={handleInputChange}
        />

        <label htmlFor="file">Imagen de Referencia</label>
        <input type="file" id="file" name="file" onChange={handleInputChange} />

        <input type="submit" value="Guardar" />
      </form>
    </div>
  );
};

export default RegistroCategoria;
