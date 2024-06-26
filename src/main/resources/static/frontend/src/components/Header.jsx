import {
  Container,
  Row,
  Col,
  Navbar,
  NavbarBrand,
  Nav,
  NavLink as RSNavLink,
  NavbarToggler,
  Collapse,
} from "reactstrap";
import { useState } from "react";
import { Link } from "react-router-dom";
import BotonesIniciarSesión from "./BotonesIniciarSesión";
import SesiónUsuario from "./SesiónUsuario";
// import { useContext } from "react";
// import { UserContext } from "../contexts/UserContext";
import "./Header.css";
import { useUsuario } from "../contexts/UsuarioContext";

function Header() {
  const { isAuthenticated, rol } = useUsuario();
  const [isOpen, setIsOpen] = useState(false);
  const toggle = () => setIsOpen(!isOpen);
  let content;
  if (isAuthenticated) {
    //Falta que compare si está autenticado o no
    console.log("iniciaste sesión");
    content = <SesiónUsuario />;
  } else {
    content = <BotonesIniciarSesión />;
  }
  // const { isAuthenticated } = useContext(UserContext);
  return (
    <Navbar className="header" light expand="md" fixed="top">
      <Container fluid={true}>
        <Row>
          <Col sm="4" className="logo-container">
            <NavbarBrand href="/">
              <img width="15%" src="/images/Logo navegador-01.png" alt="" />
              <span className="logo-text">Ruta Libre</span>
            </NavbarBrand>
          </Col>
          <Col sm="8" className="buttons-container">
            <NavbarToggler onClick={toggle} />
            <Collapse isOpen={isOpen} navbar>
              <Nav navbar>
                <div className="links-container">
                  <RSNavLink tag={Link} to="/productos">
                    Productos Favoritos
                  </RSNavLink>
                  <RSNavLink tag={Link} to="/recomendados">
                    Recomendados
                  </RSNavLink>
                  <RSNavLink tag={Link} to="/contacto">
                    Contacto
                  </RSNavLink>
                  <RSNavLink tag={Link} to="/administrador">
                    {/* Panel de Administracion */}
                    {isAuthenticated &&
                      rol === "ADMIN" &&
                      "Panel de Administracion"}
                  </RSNavLink>
                </div>
              </Nav>
              {content}
            </Collapse>
          </Col>
        </Row>
      </Container>
    </Navbar>
  );
}

export default Header;
