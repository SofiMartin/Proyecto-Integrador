import "./Productos.css";
import { useState } from "react";
import CustomCardProduct from "../components/CustomCardProduct";
import ReactPaginate from "react-paginate";
import { useUsuario } from "../contexts/UsuarioContext";
import { Container, Button, Row, Col } from "reactstrap";
import { Link } from "react-router-dom";

const Productos = () => {
  const { favs } = useUsuario();
  const [pageNumber, setPageNumber] = useState(0);
  const itemsPerPage = 6;

  // Función para obtener los productos a mostrar en la página actual
  const getDisplayedProducts = () => {
    const pagesVisited = pageNumber * itemsPerPage;
    return favs.slice(pagesVisited, pagesVisited + itemsPerPage);
  };

  // Manejar el cambio de página
  const changePage = ({ selected }) => {
    setPageNumber(selected);
  };

  return (
    <div className="recomendados">
      <h5>PRODUCTOS FAVORITOS</h5>
      <Link to="/" className="inicio">
            <Button color="secondary" className="--bs-orange">
              Volver a Inicio
            </Button>
          </Link> 
      <h2>Tus productos favoritos en un vistazo</h2>
        

      <div className="cardContainer">
        {getDisplayedProducts().map((producto) => (
          <CustomCardProduct className="CustomCardProduct" key={producto.name} producto={producto} />
        ))}
      </div>
      <ReactPaginate
        previousLabel={"Anterior"}
        nextLabel={"Siguiente"}
        pageCount={Math.ceil(favs.length / itemsPerPage)}
        onPageChange={changePage}
        containerClassName={"pagination"}
        previousLinkClassName={"previous"}
        nextLinkClassName={"next"}
        disabledClassName={"disabled"}
        activeClassName={"active"}
      />
    </div>
  );
};

export default Productos;
