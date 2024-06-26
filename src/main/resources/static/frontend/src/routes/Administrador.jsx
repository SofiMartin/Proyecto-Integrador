import { NavLink as RSNavLink } from "reactstrap";
import { Link } from "react-router-dom";
import "./Administrador.css";

const Administrador = () => {
  return (
    <div className="administrador-container">
      <div id="mensajeNoDisponible">
        Lo siento, el acceso a esta página no está disponible en dispositivos
        móviles.
      </div>

      <div className="contenido-principal">
        <h2>PANEL DE ADMINISTRACION</h2>
        <hr />

        <div className="gestion-container">
          <div className="gestion-section">
            <h3>Gestión de Productos</h3>
            <ul className="nav nav-pills">
              <li className="nav-item">
                <RSNavLink tag={Link} to="/registrarProducto">
                  <button>Agregar Producto</button>
                </RSNavLink>
                <RSNavLink tag={Link} to="/listarProducto">
                  <button>Listar Productos</button>
                </RSNavLink>
                <RSNavLink tag={Link} to="/administrarcaracteristicas">
                  <button>Administrar Características</button>
                </RSNavLink>
                <RSNavLink tag={Link} to="/administrarpoliticas">
                  <button>Administrar Políticas de Uso</button>
                </RSNavLink>
              </li>
            </ul>
          </div>

          <div className="gestion-section">
            <h3>Gestión de Categorías</h3>
            <ul className="nav nav-pills">
              <li className="nav-item">
                <RSNavLink tag={Link} to="/agregarcategoria">
                  <button>Agregar Categoría</button>
                </RSNavLink>
                <RSNavLink tag={Link} to="/listarCategorias">
                  <button>Listar Categorías</button>
                </RSNavLink>
              </li>
            </ul>
          </div>

          <div className="gestion-section">
            <h3>Gestión de Usuarios</h3>
            <ul className="nav nav-pills4">
              <li className="nav-item">
                <RSNavLink tag={Link} to="/listarUsuario">
                   <button>Listar Usuarios</button>
                </RSNavLink>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Administrador;
