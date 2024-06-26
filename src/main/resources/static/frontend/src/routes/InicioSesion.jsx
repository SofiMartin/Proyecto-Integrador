import { useState } from "react";
import { Button, Form, FormGroup, Label, Input } from "reactstrap";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useLocation } from 'react-router-dom';
import "./InicioSesion.css";
import { useUsuario } from "../contexts/UsuarioContext";
import { jwtDecode } from "jwt-decode";

const InicioSesion = () => {
  const [formData, setFormData] = useState({
    //email: "",
    username: "",
    password: "",
  });

  const { setIsAuthenticated, setUsuario, setEmail, setRol } = useUsuario();

  const navigate = useNavigate();

  const [errores, setErrores] = useState({});
  const [inicioSesionExitoso, setInicioSesionExitoso] = useState(false);
  const location = useLocation();
  const mensaje = new URLSearchParams(location.search).get('mensaje');

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Aquí puedes realizar la validación del formulario antes de enviar los datos al servidor
    const nuevosErrores = {};

    // if (!formData.email.trim()) {
    //   nuevosErrores.email = "El email es obligatorio.";
    // } else if (!/^\S+@\S+\.\S+$/.test(formData.email)) {
    //   nuevosErrores.email = "El email no es válido.";
    // }

    // if (formData.contraseña.length < 6) {
    //   nuevosErrores.contraseña =
    //     "La contraseña debe tener al menos 6 caracteres.";
    // }

    if (Object.keys(nuevosErrores).length === 0) {
      // Aquí puedes enviar los datos del formulario al servidor para el inicio de sesión
      // y manejar la respuesta del servidor
      axios
        .post("http://localhost:8080/login", formData)
        .then((res) => {
          const token = res.data.token;
          const decodedToken = jwtDecode(token);
          console.log("Token decodificado:", decodedToken);
          console.log("registro exitoso", res.data);
          localStorage.setItem("token", res.data.token);
          const userEmail = decodedToken.email;
          const userRole = decodedToken.roles[0];
          setIsAuthenticated(true);
          setUsuario(formData.username);
          setEmail(userEmail);
          setRol(userRole);

          navigate("/");
        })
        .catch((error) => console.log(error));

      // Por ejemplo, podrías mostrar un mensaje de éxito y redirigir al usuario
      setInicioSesionExitoso(true);
      setFormData({
        username: "",
        //email: "",
        password: "",
      });
      setErrores({});
    } else {
      // Si hay errores en el formulario, los mostramos
      setErrores(nuevosErrores);
      setInicioSesionExitoso(false);
    }
  };

  return (
    <div className="inicio-sesion-container">
      {mensaje && ( // Verifica si hay un mensaje enviado desde la página de reserva
        <div className="mensaje-reserva">
          <p>{mensaje}</p>
        </div>
      )}
      <h1 className="inicio-sesion-title">Iniciar Sesión</h1>
      
      {inicioSesionExitoso ? (
        <div>
          <p className="inicio-sesion-success">¡Inicio de sesión exitoso!</p>
          {/* Puedes redirigir al usuario a su página de perfil, por ejemplo */}
        </div>
      ) : (
        <Form onSubmit={handleSubmit}>
          {/* <FormGroup>
            <Label>Email</Label>
            <Input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleInputChange}
            />
            {errores.email && <p className="error-message">{errores.email}</p>}
          </FormGroup> */}
          <FormGroup>
            <Label>Username</Label>
            <Input
              type="username"
              name="username"
              value={formData.username}
              onChange={handleInputChange}
            />
            {/* {errores.email && <p className="error-message">{errores.email}</p>} */}
          </FormGroup>

          <FormGroup>
            <Label>Contraseña</Label>
            <Input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleInputChange}
            />
            {errores.contraseña && (
              <p className="error-message">{errores.contraseña}</p>
            )}
          </FormGroup>

          <Button color="primary" type="submit">
            Iniciar Sesión
          </Button>
        </Form>
      )}
    </div>
  );
};

export default InicioSesion;
