import axios from "axios";
import { useState } from "react";

const AgregarProducto = () => {
  const [formData, setFormData] = useState({
    nombre: "",
    marca: "",
    modelo: "",
    anioFabricacion: 0,
    descripcion: "",
    precioAlquiler: 0,
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

    // if (name === "file") {
    //   // Si se seleccionan imágenes, se almacenan en el estado como un array
    //   const selectedImages = Array.from(files);
    //   console.log("sle" + selectedImages);
    //   setFormData({ ...formData, [name]: selectedImages });
    // } else {
    //   // Para otros campos, se almacenan los valores directamente
    //   setFormData({ ...formData, [name]: value });
    // }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formDataToSend = new FormData();

    formDataToSend.append("nombre", formData.nombre);
    formDataToSend.append("marca", formData.marca);
    formDataToSend.append("modelo", formData.modelo);
    if (formData.anioFabricacion) {
      formDataToSend.append("anioFabricacion", formData.anioFabricacion);
    } else {
      formDataToSend.append("anioFabricacion", 0);
    }

    formDataToSend.append("descripcion", formData.descripcion);
    if (formData.precioAlquiler) {
      formDataToSend.append("precioAlquiler", formData.precioAlquiler);
    } else {
      formDataToSend.append("precioAlquiler", 0.0);
    }
    formDataToSend.append("file", formData.file);
    // formData.file.forEach((file, index) => {
    //   formDataToSend.append(`file[${index}]`, file);
    //   console.log(`Archivo ${index}:`, file);
    // });

    console.log(formData.marca);
    console.log(formData.modelo);
    console.log(formData.anioFabricacion);
    console.log(formData.descripcion);
    console.log(formData.precioAlquiler);
    console.log(formData.file);
    console.log(formDataToSend.get("file[0]"));

    try {
      axios
        .post("http://localhost:8080/motorhome/registrar", formDataToSend)
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
          <label htmlFor="nombre">Nombre</label>
          <input
            type="text"
            id="nombre"
            name="nombre"
            value={formData.nombre}
            onChange={handleInputChange}
          />
          <label htmlFor="marca">marca</label>
          <input
            type="text"
            id="marca"
            name="marca"
            value={formData.marca}
            onChange={handleInputChange}
          />
          <label htmlFor="modelo">modelo</label>
          <input
            type="text"
            id="modelo"
            name="modelo"
            value={formData.modelo}
            onChange={handleInputChange}
          />
          <label htmlFor="descripcion">descripcion</label>
          <input
            type="text"
            id="descripcion"
            name="descripcion"
            value={formData.descripcion}
            onChange={handleInputChange}
          />
          <label htmlFor="anioFabricacion">año de fabricacion</label>
          <input
            type="number"
            id="anioFabricacion"
            name="anioFabricacion"
            value={formData.anioFabricacion}
            onChange={handleInputChange}
          />
          <label htmlFor="precioAlquiler">precio</label>
          <input
            type="number"
            id="precioAlquiler"
            name="precioAlquiler"
            value={formData.precioAlquiler}
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

export default AgregarProducto;
