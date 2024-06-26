import axios from "axios";
import { useState } from "react";

const AgregarImagen = () => {
  const [formData, setFormData] = useState({
    nombre: "",
    file: "", // Cambiado de un array a un solo archivo
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

    formDataToSend.append("file", formData.file);
    // formData.file.forEach((file, index) => {
    //   formDataToSend.append(`file[${index}]`, file);
    //   console.log(`Archivo ${index}:`, file);
    // });

    console.log(formData.file);
    console.log(formData.id);

    try {
      axios
        .post("http://localhost:8080/imagenes/guardar", formDataToSend)
        .then((res) => {
          // Manejar la respuesta del servidor, por ejemplo, mostrar un mensaje de éxito
          console.log("Motorhome registrado exitosamente", res.data);
        });

      // Limpiar el formulario o realizar otras acciones necesarias
    } catch (error) {
      console.error("Error al registrar el Motorhome", error);
    }
  };

  return (
    <div
      style={{
        marginTop: 200,
        height: "100vh",
      }}
    >
      <h1>agregarProducto</h1>

      <form encType="multipart/form-data" onSubmit={handleSubmit}>
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <label htmlFor="nombre">nombre</label>
          <input
            type="text"
            id="nombre"
            name="nombre"
            value={formData.nombre}
            onChange={handleInputChange}
          />

          <label htmlFor="file">imagen</label>
          <input
            type="file"
            id="file"
            name="file"
            onChange={handleInputChange}
            multiple
          />

          <input type="submit" value="Guardar Producto" />
        </div>
      </form>
    </div>
  );
};

export default AgregarImagen;
