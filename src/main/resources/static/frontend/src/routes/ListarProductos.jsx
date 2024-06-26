import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { Button } from "reactstrap";
import Swal from "sweetalert2";
import "./ListarProductos.css";

const ListarProductos = () => {
  const [productos, setProductos] = useState([]);
  const [eliminado, setEliminado] = useState(false);

  const [actualizado, setActualizado] = useState(false);
  const [productoSeleccionado, setProductoSeleccionado] = useState(null);
  const [editedProducto, setEditedProducto] = useState({
    id: null,
    nombre: "",
    descripcion: "",
    marca: "",
    modelo: "",
    anioFabricacion: null,
    precioAlquiler: "",
    categorias: [],
    caracteristicas: [],
    //file:"",
  });
  const [allCategorias, setAllCategorias] = useState([]);
  const [allCaracteristicas, setAllCaracteristicas] = useState([]);
 
  useEffect(() => {
    axios
      .get("http://localhost:8080/categoria")
      .then((res) => {
        // Almacena las categorías disponibles en un nuevo estado
        setAllCategorias(res.data);
        console.log(res.data);
      })
      .catch((err) => console.log(err));

    axios
      .get("http://localhost:8080/caracteristica")
      .then((res) => {
        // Almacena las características en el estado
        setAllCaracteristicas(res.data);
        console.log(res.data);
      })
      .catch((err) => console.log(err));

    // Llamar a la API para obtener los productos
    axios
      .get("http://localhost:8080/motorhome")
      .then((res) => {
        setProductos(res.data);
        setEliminado(false);
        setActualizado(false);
      })
      .catch((err) => console.log(err));
  }, [eliminado, actualizado]);

  //--------------------------------------------

  // Función para manejar los cambios en las categorías seleccionadas al editar
  const handleCategoriaChange = (event, categoriaId) => {
  const isChecked = event.target.checked;

  // Si la categoría está marcada, agregarla al producto editado
  if (isChecked) {
    const updatedCategorias = [
      ...editedProducto.categorias,
      allCategorias.find((cat) => cat.id === categoriaId)
    ];
    setEditedProducto({ ...editedProducto, categorias: updatedCategorias });
  } else {
    // Si la categoría está desmarcada, quitarla del producto editado
    const updatedCategorias = editedProducto.categorias.filter(
      (cat) => cat.id !== categoriaId
    );
    setEditedProducto({ ...editedProducto, categorias: updatedCategorias });
  }
};

// Función para manejar los cambios en las caracteristicas seleccionadas al editar
const handleCaracteristicaChange = (event, caracteristicaId) => {
  const isChecked = event.target.checked;

  // Si la caracteristica está marcada, agregarla al producto editado
  if (isChecked) {
    const updatedCaracteristicas = [
      ...editedProducto.caracteristicas,
      allCaracteristicas.find((car) => car.id === caracteristicaId)
    ];
    setEditedProducto({ ...editedProducto, caracteristicas: updatedCaracteristicas });
  } else {
    // Si la caracteristica está desmarcada, quitarla del producto editado
    const updatedCaracteristicas = editedProducto.caracteristicas.filter(
      (car) => car.id !== caracteristicaId
    );
    setEditedProducto({ ...editedProducto, caracteristicas: updatedCaracteristicas });
  }
};

 //------------------------------------------
  const handleEliminarProducto = (id, nombre) => {
    Swal.fire({
      title: "ATENCION",
      text: `Estás a punto de eliminar la categoría "${nombre}"`,
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "CONFIRMAR",
      cancelButtonText: "CANCELAR",
    }).then((result) => {
      if (result.isConfirmed) {
    axios
      .delete(`http://localhost:8080/motorhome/eliminar/${id}`)
      .then(() => {
        setEliminado(true);
        console.log("Producto eliminado exitosamente");
        setProductos(productos.filter((producto) => producto.id !== id));
        Swal.fire("Producto eliminado exitosamente");
      })
      .catch((err) => console.log(err));
    console.log(`Eliminar producto con ID: ${id}`);
  }
});
};
  //---------------AGREGO FUNCION ---------------

  const handleEditarProducto = (id) => {
    axios
      .get(`http://localhost:8080/motorhome/detalle/id/${id}`)
      .then((response) => {
        setProductoSeleccionado(response.data); // Almacena los datos del producto seleccionado en el estado
        setEditedProducto({
          id: response.data.id,
          nombre: response.data.nombre,
          descripcion: response.data.descripcion,
          marca: response.data.marca,
          modelo: response.data.modelo,
          anioFabricacion: response.data.anioFabricacion,
          precioAlquiler: response.data.precioAlquiler,
          categorias: response.data.categorias,
          caracteristicas: response.data.caracteristicas,

          //file:response.data.file,
        });
      })

      .catch((error) => {
        console.error("Error al obtener los datos del producto:", error);
        // Manejar errores
      });
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setEditedProducto({ ...editedProducto, [name]: value });
    
  };

  const handleModificarProducto = () => {
    const productoFormData = new FormData();
    productoFormData.append("id", editedProducto.id);
    productoFormData.append("nombre", editedProducto.nombre);
    productoFormData.append("marca", editedProducto.marca);
    productoFormData.append("modelo", editedProducto.modelo);
    productoFormData.append("anioFabricacion", editedProducto.anioFabricacion);
    productoFormData.append("precioAlquiler", editedProducto.precioAlquiler);
    productoFormData.append("descripcion", editedProducto.descripcion);
    editedProducto.categorias.forEach((categoria) => {
      productoFormData.append("categorias", categoria);
    });

    editedProducto.caracteristicas.forEach((caracteristica) => {
      productoFormData.append("caracteristicas", caracteristica);
    });

    console.log(editedProducto.categorias);

    axios
      .put(`http://localhost:8080/motorhome/actualizar`, editedProducto)
      .then(() => {
        setActualizado(true);
        //       // Procesar la respuesta si es necesario
        Swal.fire(
          "¡Producto actualizado!",
          "El Producto se ha actualizado con éxito",
          "success"
        );
      })

      .catch(() => {
        //       // Manejar errores
      });

    };
  //-------------------------------------------------------
  console.log(allCategorias);
  return (
    <div className="listar-productos-container">
      <div className="registro-cabecera">
        <h2 className="registro-producto">Listado de Productos</h2>
        <Link to="/administrador">
          <Button color="secondary" className="--bs-orange">
            Volver
          </Button>
        </Link>
      </div>
      <hr/>
     
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Id</th>
            <th>Nombre</th>
            <th>Marca</th>
            <th>Modelo</th>
            <th>Año de Fabricación</th>
            {/* <th>Descripción</th> */}
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {productos.map((producto) => (
            <tr key={producto.id}>
              <td>{producto.id}</td>
              <td>{producto.nombre}</td>
              <td>{producto.marca}</td>
              <td>{producto.modelo}</td>
              <td>{producto.anioFabricacion}</td>
              {/* <td>{producto.descripcion}</td> */}
              <td>
                <button
                  className="btn btn-danger"
                  onClick={() => handleEliminarProducto(producto.id, producto.nombre)}
                >
                  Eliminar
                </button>
                <button
                  className="btn btn-warning d-inline mx-2"
                  onClick={() => handleEditarProducto(producto.id)}
                >
                  Editar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {/* Mostrar el formulario de edición si hay un producto seleccionado */}
      {productoSeleccionado && (
        <div>
          <h2>Editar Producto</h2>
          <form>
            <div>
              <label>Nombre:</label>
              <input
                type="text"
                name="nombre"
                value={editedProducto.nombre}
                onChange={handleInputChange}
              />
            </div>
            <div>
              <label>Marca:</label>
              <input
                type="text"
                name="marca"
                value={editedProducto.marca}
                onChange={handleInputChange}
              />
            </div>
            <div>
              <label>Modelo:</label>
              <input
                type="text"
                name="modelo"
                value={editedProducto.modelo}
                onChange={handleInputChange}
              />
            </div>
            <div>
              <label>Año de Fabricación:</label>
              <input
                type="text"
                name="anioFabricacion"
                value={editedProducto.anioFabricacion}
                onChange={handleInputChange}
              />
            </div>
            <div>
              <label>Precio de Alquiler:</label>
              <input
                type="number"
                name="precioAlquiler"
                value={editedProducto.precioAlquiler}
                onChange={handleInputChange}
              />
            </div>
            <div>
              <label>Descripción:</label>
              <input
                type="text"
                name="descripcion"
                value={editedProducto.descripcion}
                onChange={handleInputChange}
              />
            </div>
            <div>
              <label>Categorías:</label>
              <div className="checkbox-container">
                {allCategorias.map((categoria) => (
                  <div key={categoria.id}>
                    <label className="checkbox-label"></label>
                    <input
                      type="checkbox"
                      value={categoria.id}
                      onChange={(e) => handleCategoriaChange(e, categoria.id)}
                      checked={editedProducto.categorias.some(
                        (cat) => cat.id === categoria.id
                      )}
                    />
                    <label className="category-label">{categoria.nombre}</label>
                  </div>
                ))}
                </div>
              </div>
              <div>
              <label>Características:</label>
              <div className="checkbox-container">
                {allCaracteristicas.map((caracteristica) => (
                  <div key={caracteristica.id}>
                     <label className="checkbox-label"></label>
                     <input
                      type="checkbox"
                      value={caracteristica.id}
                      onChange={(e) => handleCaracteristicaChange(e, caracteristica.id)}
                      checked={editedProducto.caracteristicas.some(
                        (car) => car.id === caracteristica.id
                      )}
                    />
                    <span className="checkmark" />
                    <label className="category-label">{caracteristica.descripcion}</label>
                  </div>
                ))}
                </div>
              </div>
           
            <button type="button" onClick={handleModificarProducto}>
              Guardar cambios
            </button>
          </form>
        </div>
      )}
    </div>
  );
};

export default ListarProductos;
