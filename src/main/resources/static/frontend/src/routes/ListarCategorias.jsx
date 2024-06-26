import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "reactstrap";
import axios from "axios";
import Swal from "sweetalert2";
import "./ListarCategorias.css";

const ListarCategorias = () => {
  const [categorias, setCategorias] = useState([]);
  const [eliminado, setEliminado] = useState(false);
  const [categoriaSeleccionada, setCategoriaSeleccionada] = useState(null);

  useEffect(() => {
    axios
      .get("http://localhost:8080/categoria")
      .then((res) => {
        setCategorias(res.data);
      })
      .catch((err) => console.log(err));
  }, [eliminado]);

  //--------------------------------------------
  const [editedCategoria, setEditedCategoria] = useState({
    id: null,
    nombre: "",
    descripcion: "",
    file: "",
  });

  //------------------------------------------
  const handleEliminarCategoria = (id, nombre) => {
    Swal.fire({
      title: "ATENCION",
      text: `Estás a punto de eliminar la categoría "${nombre}". Con esta acción se podrían borrar los productos asociados.`,
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "CONFIRMAR",
      cancelButtonText: "CANCELAR",
    }).then((result) => {
      if (result.isConfirmed) {
        axios
          .delete(`http://localhost:8080/categoria/eliminar/${id}`)
          .then(() => {
            setEliminado(true);
            console.log("Categoria eliminada correctamente");
            // Actualizar la lista de categorías eliminando la categoría eliminada
            setCategorias(
              categorias.filter((categoria) => categoria.id !== id)
            );
            Swal.fire("Categoría borrada exitosamente");
          })
          .catch((err) => console.log(err));
        console.log(`Eliminar categoria con ID: ${id}`);
        Swal.fire({
          position: "top-end",
          icon: "success",
          title: "Categoría Eliminada con éxito",
          showConfirmButton: false,
          timer: 1500,
        });
      }
    });
  };
  //---------------AGREGO FUNCION ---------------
  const handleEditarCategoria = (id) => {
    axios
      .get(`http://localhost:8080/categoria/detalle/${id}`)
      .then((response) => {
        setCategoriaSeleccionada(response.data); // Almacena los datos de la categoria seleccionada en el estado
        setEditedCategoria({
          id: id,
          nombre: response.data.nombre,
          descripcion: response.data.descripcion,
          file: response.data.file,
        });
      })

      .catch((error) => {
        console.error("Error al obtener los datos de la categoria:", error);
      });
  };

  const handleInputChange = (event) => {
    const { name, files } = event.target;

    if (name === "file") {
      // Acceder al primer archivo seleccionado si hay alguno
      const selectedFile = files && files.length > 0 ? files[0] : null;

      // Hacer algo con el archivo seleccionado, como almacenarlo en el estado
      setEditedCategoria({ ...editedCategoria, file: selectedFile });
    } else {
      setEditedCategoria({ ...editedCategoria, [name]: event.target.value });
    }
  };

  console.log(editedCategoria.id);

  const handleModificarCategoria = () => {
    const categoriaFormData = new FormData();
    categoriaFormData.append("id", editedCategoria.id);
    categoriaFormData.append("nombre", editedCategoria.nombre);
    categoriaFormData.append("descripcion", editedCategoria.descripcion);
    categoriaFormData.append("file", editedCategoria.file);

    // const fileFormData = new FormData();
    // fileFormData.append("file", editedCategoria.file);
    // console.log(FormData)

    // Enviar la categoría primero
    axios
      .put(`http://localhost:8080/categoria/actualizar`, categoriaFormData)
      .then(() => {
        // Procesar la respuesta si es necesario
        Swal.fire(
          "¡Categoría actualizada!",
          "La categoría se ha actualizado con éxito.",
          "success"
        );
      })
      .catch((error) => {
        console.error("Error al actualizar los datos de la categoria", error);
        Swal.fire(
          "¡Error!",
          "Hubo un problema al actualizar la categoria.",
          "error"
        );
      });
  };
  //-------------------------------------------------------
  return (
    <div className="listar-categorias-container">
      <div className="registro-cabecera-Cat">
        <h2>Lista de Categorias</h2>
        <Link to="/administrador">
          <Button color="secondary" className="--bs-orange">
            Volver
          </Button>
        </Link>
      </div>
      <hr />
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Id</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {categorias.map((categoria) => (
            <tr key={categoria.id}>
              <td>{categoria.id}</td>
              <td>{categoria.nombre}</td>
              <td>{categoria.descripcion}</td>
              <td className="acciones-cell">
                <button
                  className="btn btn-danger d-inline mx-2"
                  onClick={() =>
                    handleEliminarCategoria(categoria.id, categoria.nombre)
                  }
                >
                  Eliminar
                </button>
                <button
                  className="btn btn-warning d-inline mx-2"
                  onClick={() => handleEditarCategoria(categoria.id)}
                >
                  Editar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {/* Mostrar el formulario de edición si hay una categoria seleccionada */}
      {categoriaSeleccionada && (
        <div>
          <h2>Editar Categoria</h2>
          <form encType="multipart/form-data">
            <div>
              <label>Nombre:</label>
              <input
                type="text"
                name="nombre"
                value={editedCategoria.nombre}
                onChange={handleInputChange}
              />
            </div>
            <div>
              <label>Descripción:</label>
              <input
                type="text"
                name="descripcion"
                value={editedCategoria.descripcion}
                onChange={handleInputChange}
              />
            </div>
            <div>
              <label htmlFor="file" style={{ display: "block" }}>
                Imágen Representativa:
              </label>
              <img
                src={`images/${editedCategoria.file}`}
                alt=""
                width="150px"
              />
              <input type="file" name="file" onChange={handleInputChange} />
              {editedCategoria.imagen && (
                <img src="images/editedCategoria.file.name" alt="" />
              )}
            </div>
            <button type="button" onClick={handleModificarCategoria}>
              Guardar cambios
            </button>
          </form>
        </div>
      )}
    </div>
  );
};

export default ListarCategorias;
