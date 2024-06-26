import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useUsuario } from "../contexts/UsuarioContext";
// import { useContext } from "react";

const Cierre = () => {
  const { setIsAuthenticated} = useUsuario();
  const navigate = useNavigate();

  console.log ("")
  // Función para manejar el cierre de sesión
  const handleLogout = () => {
    // Agrega la lógica de cierre de sesión aquí
    
    // Por ejemplo, puedes borrar el token de autenticación almacenado en localStorage
    localStorage.removeItem("token");
     setIsAuthenticated(false);
    // Luego, redirige a la página de inicio de sesión
    navigate("/"); // Redirige a la ruta de inicio de sesión
  };
  return (
    <div>
        <Button
        type="submit"
        variant="contained"
        color="primary"
        onClick={handleLogout}
        >
        Cerrar Sesion
      </Button>
    </div>
  );
};

export default Cierre;