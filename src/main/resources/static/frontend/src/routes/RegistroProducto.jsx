import axios from "axios";
import Swal from "sweetalert2";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Button } from "reactstrap";
import "./RegistroProductos.css";

const RegistroProducto = () => {
  const [formData, setFormData] = useState({
    nombre: "",
    marca: "",
    modelo: "",
    anioFabricacion: 0,
    descripcion: "",
    precioAlquiler: 0.0,
    categorias: [], // Cambiado a un arreglo para almacenar objetos de categoría
    caracteristicas: [],
  });

  const [imagenes, setImagenes] = useState({
    nombre: "",
    file: [], // Cambiado de un array a un solo archivo
  });

  const [categorias, setCategorias] = useState([]);
  const [selectedCategorias, setSelectedCategorias] = useState([]);
  const [caracteristicas, setCaracteristicas] = useState([]);
  const [selectedFeatures, setSelectedFeatures] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/categoria")
      .then((res) => {
        setCategorias(res.data);
      })
      .catch((err) => console.log(err));
  }, []);
  
  useEffect(() => {
    axios
      .get("http://localhost:8080/caracteristica")
      .then((res) => {
        setCaracteristicas(res.data);
      })
      .catch((err) => console.log(err));
  }, []);

   const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleFileChange = (e) => {
    setImagenes({ ...imagenes, file: e.target.files }); // Almacena las imágenes seleccionadas en el estado
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Actualizar el estado formData con las categorías seleccionadas
    const selectedCategorias2 = categorias.filter((categoria) =>
      selectedCategorias.includes(categoria.id)
    );
    // Actualizar el estado formData con las características seleccionadas
    const selectedCaracteristicas = caracteristicas.filter((caracteristica) =>
      selectedFeatures.includes(caracteristica.id)
    );

    console.log("Selected categories:", selectedCategorias2);
    console.log("Selected features:", selectedCaracteristicas);
    console.log("Form data before submission:", formData);

  // Agregar las características seleccionadas al formData
  setFormData({ ...formData,
    categorias: selectedCategorias2,
    caracteristicas: selectedCaracteristicas,
  });

    axios
      .post("http://localhost:8080/motorhome/registrar", {
        ...formData,
        categorias: selectedCategorias2,
        caracteristicas: selectedCaracteristicas,
      })
      .then((res) => {
        console.log("Motorhome registrado exitosamente", res.data);
        Swal.fire({
          icon: "success",
          title: "Exito",
          text: "Registro Exitoso",
        });
        // Si el producto se registra exitosamente, procede a registrar las imágenes
        const productoNom = res.data.nombre;

        if (imagenes.file && imagenes.file.length > 0) {
          const formDataImagenes = new FormData();

          formDataImagenes.append("nombre", productoNom);

          for (let i = 0; i < imagenes.file.length; i++) {
            formDataImagenes.append("file", imagenes.file[i]);
          }

          axios
            .post(`http://localhost:8080/imagenes/guardar`, formDataImagenes)
            .then((res) => {
              console.log("Imágenes registradas exitosamente", res.data);
            })
            .catch((error) => {
              console.error("Error al registrar las imágenes", error);
              alert("Error al registrar las imágenes");
            });
        } else {
          console.log("Error al registrar las imágenes");
        }
      })
      .catch((error) => {
        if (error.response) {
          // El servidor respondió con un código de estado diferente de 2xx
          console.error("Error al registrar el Motorhome", error.response.data);

          // Verificar si el error es debido a un producto duplicado
          if (error.response.status === 409) {
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "El nombre ya esta en uso",
            });
          } else {
            alert("Error al registrar el Motorhome");
          }
        } else if (error.request) {
          // La solicitud se hizo pero no se recibió respuesta
          console.error(
            "Error al registrar el Motorhome",
            "No se recibió respuesta del servidor"
          );
          alert(
            "Error al registrar el Motorhome. No se recibió respuesta del servidor."
          );
        } else {
          // Otro tipo de error
          console.error("Error al registrar el Motorhome", error.message);
          alert("Error al registrar el Motorhome. " + error.message);
        }
      });
  };

  const handleCategoriaClick = (id) => {
    const index = selectedCategorias.indexOf(id);
    if (index === -1) {
      setSelectedCategorias([...selectedCategorias, id]); // Agrega la categoría seleccionada
    } else {
      const updatedCategorias = [...selectedCategorias];
      updatedCategorias.splice(index, 1); // Elimina la categoría si ya está seleccionada
      setSelectedCategorias(updatedCategorias);
    }
  };

  const handleFeatureClick = (id) => {
    // Función para manejar el clic en una característica
    const index = selectedFeatures.indexOf(id);
    if (index === -1) {
      setSelectedFeatures([...selectedFeatures, id]); // Agrega la característica seleccionada
    } else {
      const updatedFeatures = [...selectedFeatures];
      updatedFeatures.splice(index, 1); // Elimina la característica si ya está seleccionada
      setSelectedFeatures(updatedFeatures);
    }
  };

  return (
    <div className="registro-container">
      <div className="registro-cabecera">
        <h2 className="registro-productos">Registro de Producto</h2>
        <Link to="/administrador">
          <Button color="secondary" className="--bs-orange">
            Volver
          </Button>
        </Link>
      </div>
      <hr/>
      <form encType="multipart/form-data" onSubmit={handleSubmit}>
        <label htmlFor="nombre">Nombre</label>
        <input
          type="text"
          id="nombre"
          name="nombre"
          value={formData.nombre}
          onChange={handleInputChange}
        />
        <label htmlFor="marca">Marca</label>
        <input
          type="text"
          id="marca"
          name="marca"
          value={formData.marca}
          onChange={handleInputChange}
        />
        <label htmlFor="modelo">Modelo</label>
        <input
          type="text"
          id="modelo"
          name="modelo"
          value={formData.modelo}
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
        <label htmlFor="anioFabricacion">Año de fabricación</label>
        <input
          type="number"
          id="anioFabricacion"
          name="anioFabricacion"
          value={formData.anioFabricacion}
          onChange={handleInputChange}
        />
        <label htmlFor="precioAlquiler">Precio de Alquiler por Día</label>
        <input
          type="number"
          id="precioAlquiler"
          name="precioAlquiler"
          value={formData.precioAlquiler}
          onChange={handleInputChange}
        />

        <label htmlFor="categorias">Categorías (selección múltiple)</label>
        <div className="categorias-container">
        {categorias.map((categoria) => (
          <div
            key={categoria.id}
            className={
              selectedCategorias.includes(categoria.id)
                ? "categoria-item selected" // Aplica el estilo si está seleccionada
                : "categoria-item"
            }
            onClick={() => handleCategoriaClick(categoria.id)} // Maneja el clic en la categoria
          >
            {categoria.nombre}
          </div>
        ))}
      </div>

      <label htmlFor="caracteristicas">Características (selección múltiple)</label>
       <div className="caracteristicas-container">
        {caracteristicas.map((caracteristica) => (
          <div
            key={caracteristica.id}
            className={
              selectedFeatures.includes(caracteristica.id)
                ? "caracteristica-item selected" // Aplica el estilo si está seleccionada
                : "caracteristica-item"
            }
            onClick={() => handleFeatureClick(caracteristica.id)} // Maneja el clic en la característica
          >
            {caracteristica.descripcion}
          </div>
        ))}
      </div>

        <label htmlFor="file">Imagen de Referencia</label>
        <input
          type="file"
          id="file"
          name="file"
          multiple
          onChange={handleFileChange}
        />

        <input type="submit" value="Guardar" />
      </form>
      {/* </Col> */}
    </div>
  );
};

export default RegistroProducto;
