import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { Button } from "reactstrap";
import "./ListarUsuarios.css";

const ListarUsuarios = () => {
  const [usuarios, setUsuarios] = useState([]);
  const [eliminado, setEliminado] = useState(false);
  const [actualizado, setActualizado] = useState(false);

  useEffect(() => {
    axios
      .get("http://localhost:8080/usuarios")
      .then((res) => {
        setUsuarios(res.data);
        setEliminado(false);
        setActualizado(false);
      })
      .catch((err) => console.log(err));
  }, [eliminado, actualizado]);

  const handleEliminarUsuario = (id) => {
    axios
      .delete("http://localhost:8080/usuarios/eliminar/" + id)
      .then((res) => {
        setEliminado(true);
        console.log(res);
      })
      .catch((err) => console.log(err));
    // Aquí puedes implementar la lógica para eliminar un producto
    // Puedes usar una función o modal de confirmación antes de eliminar.
    console.log(`Eliminar usuario con ID: ${id}`);
  };

  const handleEditarProducto = (id) => {
    axios
      .put("http://localhost:8080/usuarios/rol/" + id)
      .then((res) => {
        setActualizado(true);
        console.log(res);
      })
      .catch((err) => console.log(err));
  };
  // console.log(usuarios.roles[0].id);
  return (
    <div className="listar-usuarios-container">
      <div className="registro-cabecera">
      <h2>Lista de Usuarios</h2>
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
            <th>email</th>
            <th>Rol</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {usuarios.map((usuario) => (
            <tr key={usuario.id}>
              <td>{usuario.id}</td>
              <td>{usuario.username}</td>
              <td>{usuario.email}</td>
              <td>{usuario.roles[0].name}</td>
              <td>
                <button
                  className="btn btn-danger"
                  onClick={() => handleEliminarUsuario(usuario.id)}
                >
                  Eliminar
                </button>
                <button
                  className="btn btn-warning d-inline mx-2"
                  onClick={() => handleEditarProducto(usuario.id)}
                >
                  Cambiar Rol
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ListarUsuarios;
