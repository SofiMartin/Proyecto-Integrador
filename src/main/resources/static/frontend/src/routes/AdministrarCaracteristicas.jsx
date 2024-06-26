import { useEffect, useState, useRef } from "react";
import { NavLink as RSNavLink } from "reactstrap";
import { Link } from "react-router-dom";
import { Button } from "reactstrap";
import axios from "axios";
import Swal from "sweetalert2";
import "./AdministrarCaracteristicas.css";

const AdministrarCaracteristicas = () => {
  const [caracteristicas, setCaracteristicas] = useState([]);
  const [eliminado, setEliminado] = useState(false);
  const [caracteristicaSeleccionada, setCaracteristicaSeleccionada] =
    useState(null);
  const formularioEdicionRef = useRef(null);

  const scrollToFormularioEdicion = () => {
    formularioEdicionRef.current.scrollIntoView({
      behavior: "smooth",
      // block: "start", // Asegura que el borde superior del formulario sea visible
      inline: "nearest", // Desplazamiento suave
      offset: -500, // Ajuste para desplazarse un poco más arriba
    });
  };

  useEffect(() => {
    axios
      .get("http://localhost:8080/caracteristica")
      .then((res) => {
        setCaracteristicas(res.data);
      })
      .catch((err) => console.log(err));
  }, [eliminado]);

  //--------------------------------------------
  const [editedCaracteristica, setEditedCaracteristica] = useState({
    id: null,
    descripcion: "",
    file: "",
  });

  //------------------------------------------
  const handleEliminarCaracteristica = (id, descripcion) => {
    Swal.fire({
      title: "ATENCION",
      text: `Estás a punto de eliminar la caracteristica "${descripcion}".`,
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "CONFIRMAR",
      cancelButtonText: "CANCELAR",
    }).then((result) => {
      if (result.isConfirmed) {
        axios
          .delete(`http://localhost:8080/caracteristica/eliminar/${id}`)
          .then(() => {
            setEliminado((prevState) => !prevState); // Actualizar el estado para refrescar la lista
            Swal.fire("Caracteristica borrada exitosamente");
          })
          .catch((err) => console.log(err));
        console.log(`Eliminar caracteristica con ID: ${id}`);
        Swal.fire({
          position: "top-end",
          icon: "success",
          title: "Caracteristica Eliminada con éxito",
          showConfirmButton: false,
          timer: 1500,
        });
      }
    });
  };

  const handleEditarCaracteristica = (id) => {
    axios
      .get(`http://localhost:8080/caracteristica/detalle/${id}`)
      .then((response) => {
        setCaracteristicaSeleccionada(response.data); // Almacena los datos de la caracteristica seleccionada en el estado
        setEditedCaracteristica({
          id: id,
          descripcion: response.data.descripcion,
          file: response.data.file,
        });
      })

      .catch((error) => {
        console.error(
          "Error al obtener los datos de la caracteristica:",
          error
        );
      });
  };

  const handleInputChange = (event) => {
    const { name, value, files } = event.target;

    if (name === "file") {
      // Acceder al primer archivo seleccionado si hay alguno
      const selectedFile = files && files.length > 0 ? files[0] : null;

      // Hacer algo con el archivo seleccionado, como almacenarlo en el estado
      setEditedCaracteristica({ ...editedCaracteristica, file: selectedFile });
    } else {
      setEditedCaracteristica({ ...editedCaracteristica, [name]: value });
    }
  };

  console.log(editedCaracteristica.id);

  const handleModificarCaracteristica = () => {
    const caracteristicaFormData = new FormData();
    caracteristicaFormData.append("id", editedCaracteristica.id);
    caracteristicaFormData.append(
      "descripcion",
      editedCaracteristica.descripcion
    );
    caracteristicaFormData.append("file", editedCaracteristica.file);

    // const fileFormData = new FormData();
    // fileFormData.append("file", editedCaracteristica.file);
    console.log(editedCaracteristica.descripcion);

    axios
      .put(
        `http://localhost:8080/caracteristica/actualizar`,
        caracteristicaFormData
      )
      .then(() => {
        Swal.fire(
          "¡Característica actualizada!",
          "La característica se ha actualizado con éxito.",
          "success"
        );
      })
      .catch((error) => {
        console.error(
          "Error al actualizar los datos de la característica:",
          error
        );
        Swal.fire(
          "¡Error!",
          "Hubo un problema al actualizar la característica.",
          "error"
        );
      });
  };

  return (
    <div className="listar-caracteristicas-container">
      <div className="registro-cabecera-Cara">
        <h2 style={{ marginRight: "40px" }}>
          Administración de Caracteristicas
        </h2>
        <Link to="/administrador">
          <Button color="secondary" className="--bs-orange">
            Volver
          </Button>
        </Link>
      </div>
      <hr />
      <RSNavLink tag={Link} to="/registroCaracteristica">
        <button style={{ width: "300px", marginBottom: "20px" }}>
          Agregar nueva Caracteristica
        </button>
      </RSNavLink>
      <h2>Lista de Caracteristicas</h2>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Id</th>
            <th>Ícono</th>
            <th>Descripcion</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {caracteristicas.map((caracteristica) => (
            <tr key={caracteristica.id}>
              <td>{caracteristica.id}</td>
              <td>
                {caracteristica.file && ( // Verificar si hay un archivo asociado
                  <img
                    src={`../images/${caracteristica.file}`}
                    alt={`Ícono de ${caracteristica.descripcion}`}
                    className="icono-caracteristica"
                  />
                )}
              </td>
              <td>{caracteristica.descripcion}</td>
              <td>
                <button
                  className="btn btn-danger d-inline mx-2"
                  onClick={() =>
                    handleEliminarCaracteristica(
                      caracteristica.id,
                      caracteristica.descripcion
                    )
                  }
                >
                  Eliminar
                </button>
                <button
                  className="btn btn-warning d-inline mx-2"
                  onClick={() => {
                    handleEditarCaracteristica(caracteristica.id);
                    scrollToFormularioEdicion();
                  }}
                >
                  Editar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {/* Mostrar el formulario de edición si hay una característica seleccionada */}
      {caracteristicaSeleccionada && (
        <div ref={formularioEdicionRef}>
          <h2>Editar Característica</h2>
          <form encType="multipart/form-data">
            <div>
              <label>Descripción:</label>
              <input
                type="text"
                name="descripcion"
                value={editedCaracteristica.descripcion}
                onChange={handleInputChange}
              />
            </div>
            <div>
              <label htmlFor="file" style={{ display: "block" }}>
                Ícono:
              </label>
              <img
                src={`images/${editedCaracteristica.file}`}
                alt=""
                width="150px"
              />
              <input type="file" name="file" onChange={handleInputChange} />
              {editedCaracteristica.imagen && (
                <img src="images/editedCaracteristica.file.name" alt="" />
              )}
            </div>
            {/* Otros campos del formulario según tus necesidades */}
            <button type="button" onClick={handleModificarCaracteristica}>
              Guardar cambios
            </button>
          </form>
        </div>
      )}
    </div>
  );
};

export default AdministrarCaracteristicas;
