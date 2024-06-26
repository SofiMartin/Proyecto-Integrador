import { useUsuario } from "../contexts/UsuarioContext";
import "./SesiónUsuario.css";
import PerfilBotón from "./PerfilBotón";

function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1);
}

function SesiónUsuario() {
  const { usuario } = useUsuario();

  // Suponiendo que el formato de usuario es "Nombre Apellido"
  //   const [nombre, apellido] = usuario.split(" ");

  //   const nombreFormateado = capitalizeFirstLetter(nombre);
  //   const apellidoFormateado = capitalizeFirstLetter(apellido);

  //   {nombreFormateado.charAt(0)}{apellidoFormateado.charAt(0)}

  const aliasFormateado = capitalizeFirstLetter(usuario);

  return (
    <div className="SesiónIniciada">
      <PerfilBotón className="MenuUsuario" inicialNombre={aliasFormateado.charAt(0)} nombre={usuario} />
      <p className="NombreUsuario">{usuario}</p>
      
      {/*Se debe de traer La inicial del nombre y del Apellido Ejemplo: Carlos Perez*/}
    </div>
  );
}
export default SesiónUsuario;
