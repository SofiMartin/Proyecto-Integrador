import { useEffect, useState } from "react";
import { NavLink as RSNavLink } from "reactstrap";
import { Button } from "reactstrap";
import { Link } from "react-router-dom";
import axios from "axios";
import Swal from "sweetalert2";
import "./AdministrarPoliticas.css";
import "bootstrap/dist/css/bootstrap.min.css";

const AdministrarPoliticas = () => {
  const [politicas, setPoliticas] = useState([]);
  const [eliminado, setEliminado] = useState(false);
  const [politicaSeleccionada, setPoliticaSeleccionada] = useState(null);

  useEffect(() => {
    axios
      .get("http://localhost:8080/politica")
      .then((res) => {
        setPoliticas(res.data);
      })
      .catch((err) => console.log(err));
  }, [eliminado]);

  //--------------------------------------------
  const [editedPolitica, setEditedPolitica] = useState({
    id: null,
    nombre: "",
    descripcion: "",
  });
  //------------------------------------------
  const handleEliminarPolitica = (id, nombre) => {
    Swal.fire({
      title: "ATENCION",
      text: `Estás a punto de eliminar la Política: "${nombre}".`,
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "CONFIRMAR",
      cancelButtonText: "CANCELAR",
    }).then((result) => {
      if (result.isConfirmed) {
        axios
          .delete(`http://localhost:8080/politica/eliminar/${id}`)
          .then(() => {
            setEliminado(true);
            console.log("Politica eliminada correctamente");
            // Actualizar la lista de políticas eliminando la politica eliminada
            setPoliticas(politicas.filter((politica) => politica.id !== id));
            Swal.fire("Política borrada exitosamente");
          })
          .catch((err) => console.log(err));
        console.log(`Eliminar politica con ID: ${id}`);
        Swal.fire({
          position: "top-end",
          icon: "success",
          title: "Política Eliminada con éxito",
          showConfirmButton: false,
          timer: 1500,
        });
      }
    });
  };

  const handleEditarPolitica = (id) => {
    axios
      .get(`http://localhost:8080/politica/detalle/${id}`)
      .then((response) => {
        setPoliticaSeleccionada(response.data); // Almacena los datos de la politica seleccionada en el estado
        setEditedPolitica({
          id: id,
          nombre: response.data.nombre,
          descripcion: response.data.descripcion,
        });
      })

      .catch((error) => {
        console.error("Error al obtener los datos de la politica:", error);
      });
  };

  const handleInputChange = (event) => {
    const { name } = event.target;

    setEditedPolitica({ ...editedPolitica, [name]: event.target.value });
  };
  const handleModificarPolitica = () => {
    const politicaFormData = new FormData();
    politicaFormData.append("id", editedPolitica.id);
    politicaFormData.append("nombre", editedPolitica.nombre);
    politicaFormData.append("descripcion", editedPolitica.descripcion);

    // Enviar la política actualizada
    axios
      .put(`http://localhost:8080/politica/actualizar`, politicaFormData)
      .then(() => {
        // Procesar la respuesta si es necesario
        Swal.fire(
          "¡Politica actualizada!",
          "La politica se ha actualizado con éxito.",
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

  return (
    <div className="listar-politicas-container">
      <div className="registro-cabecera-Poli">
        <h2 style={{ marginRight: "40px" }}>Administración de Políticas</h2>
        <Link to="/administrador">
          <Button color="secondary" className="--bs-orange">
            Volver
          </Button>
        </Link>
      </div>
      <hr />
      <RSNavLink tag={Link} to="/registroPolitica">
        <button style={{ width: "300px", marginBottom: "20px" }}>
          Agregar nueva Política
        </button>
      </RSNavLink>
      <h2>Lista de Políticas</h2>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Id</th>
            <th>Nombre</th>
            <th>Descripcion</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {politicas.map((politica) => (
            <tr key={politica.id}>
              <td>{politica.id}</td>
              <td>{politica.nombre}</td>
              <td>{politica.descripcion}</td>
              <td>
                <button
                  className="btn btn-danger d-inline mx-2"
                  onClick={() =>
                    handleEliminarPolitica(politica.id, politica.nombre)
                  }
                >
                  Eliminar
                </button>
                <button
                  className="btn btn-warning d-inline mx-2"
                  onClick={() => handleEditarPolitica(politica.id)}
                >
                  Editar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Mostrar el formulario de edición si hay una politica seleccionada */}
      {politicaSeleccionada && (
        <div>
          <h2>Editar Politica</h2>
          <form encType="multipart/form-data">
            <div>
              <label>Nombre:</label>
              <input
                type="text"
                name="nombre"
                value={editedPolitica.nombre}
                onChange={handleInputChange}
              />
            </div>
            <div>
              <label>Descripción:</label>
              <div></div>
              <textarea
                type="text"
                name="descripcion"
                value={editedPolitica.descripcion}
                onChange={handleInputChange}
                style={{
                  height: "200px",
                  textAlign: "justify",
                  width: "100%",
                  resize: "none",
                  borderRadius: "5px",
                  color: "#333", // Color del texto similar al del input
                  borderColor: "#ccc",
                }}
              />
            </div>

            <button type="button" onClick={handleModificarPolitica}>
              Guardar cambios
            </button>
          </form>
        </div>
      )}
    </div>
  );
};

export default AdministrarPoliticas;
