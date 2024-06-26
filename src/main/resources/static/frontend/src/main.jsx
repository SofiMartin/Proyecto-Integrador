import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { CategoryProvider } from "./contexts/CategoryContext.jsx";
import { UsuarioProvider } from "./contexts/UsuarioContext.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <UsuarioProvider>
      <CategoryProvider>
        <App />
      </CategoryProvider>
    </UsuarioProvider>
  </React.StrictMode>
);
