import { createContext, useState, useEffect } from "react";
import PropTypes from "prop-types";

export const UserContext = createContext();

const AuthContextProvider = ({ children }) => {
  // const [csrfToken, setCsrfToken] = useState(""); // Estado para almacenar el token CSRF
  const [isAuthenticated, setIsAuthenticated] = useState(
    localStorage.getItem("isAuthenticated") === "true"
  );

  useEffect(() => {
    localStorage.setItem("isAuthenticated", isAuthenticated);
  }, [isAuthenticated]);

  const data = {
    isAuthenticated,
    setIsAuthenticated,
  };

  return <UserContext.Provider value={data}>{children}</UserContext.Provider>;
};

AuthContextProvider.propTypes = {
  children: PropTypes.node.isRequired,
};

export default AuthContextProvider;
