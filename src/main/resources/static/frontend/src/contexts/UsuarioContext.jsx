import { createContext, useState, useEffect, useContext } from "react";
import PropTypes from "prop-types";

const UsuarioContext = createContext();

export const UsuarioProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(
    localStorage.getItem("isAuthenticated") === "true"
  );

  const localFavs = JSON.parse(localStorage.getItem("favs"));
  const initialFavState = localFavs ? localFavs : [];
  const [usuario, setUsuario] = useState(localStorage.getItem("usuario"));
  const [email, setEmail] = useState(localStorage.getItem("email"));
  const [rol, setRol] = useState(localStorage.getItem("rol"));
  const [favs, setFavs] = useState(initialFavState);

  useEffect(() => {
    localStorage.setItem("isAuthenticated", isAuthenticated);
    localStorage.setItem("usuario", usuario);
    localStorage.setItem("email", email);
    localStorage.setItem("rol", rol);
  }, [isAuthenticated, usuario, email, rol]);

  useEffect(() => {
    localStorage.setItem("favs", JSON.stringify(favs));
  }, [favs]);

  //   const data = {
  //     isAuthenticated,
  //     setIsAuthenticated,
  //   };

  return (
    <UsuarioContext.Provider
      value={{
        isAuthenticated,
        setIsAuthenticated,
        usuario,
        setUsuario,
        email,
        setEmail,
        rol,
        setRol,
        favs,
        setFavs,
      }}
    >
      {children}
    </UsuarioContext.Provider>
  );
};

UsuarioProvider.propTypes = {
  children: PropTypes.node.isRequired,
};

export const useUsuario = () => {
  return useContext(UsuarioContext);
};
