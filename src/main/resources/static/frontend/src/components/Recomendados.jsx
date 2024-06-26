import { useState, useEffect } from "react";
import CustomCardProduct from "./CustomCardProduct";
import axios from "axios";
import ReactPaginate from "react-paginate";
import { useCategory } from "../contexts/CategoryContext";
import "./Recomendados.css";

function Recomendados() {
  const [motorhome, setMotorhome] = useState([]);
  const [pageNumber, setPageNumber] = useState(0);
  const itemsPerPage = 6;

  const { selectedCategoryIds } = useCategory();

  console.log(selectedCategoryIds);

  useEffect(() => {
    const fetchMotorhomes = async () => {
      try {
        let url = "http://localhost:8080/motorhome";

        if (selectedCategoryIds.size > 0) {
          const categoryIds = Array.from(selectedCategoryIds).join(",");
          url = `http://localhost:8080/motorhome/categoria/all?categoriaIds=${categoryIds}`;
        }

        const response = await axios.get(url);

        const uniqueMotorhomes = [...new Set(response.data)];
        const shuffledMotorhome = shuffleArray(uniqueMotorhomes);
        setMotorhome(shuffledMotorhome);
      } catch (error) {
        console.error("Error fetching motorhomes:", error);
      }
    };

    fetchMotorhomes();
  }, [selectedCategoryIds]);

  // Función para obtener un orden aleatorio de los productos
  const shuffleArray = (array) => {
    const shuffled = array.slice();
    for (let i = shuffled.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
    }
    return shuffled;
  };

  // Paginación
  const pagesVisited = pageNumber * itemsPerPage;
  const displayItems = motorhome.slice(
    pagesVisited,
    pagesVisited + itemsPerPage
  );

  // Manejar el cambio de página
  const changePage = ({ selected }) => {
    setPageNumber(selected);
  };

  return (
    <div className="Recomendados">
      <h5>TOP MOTORHOMES</h5>
      <h2>Las mejores ofertas para tu viaje</h2>

      <div className="cardContainer">
        {displayItems.map((producto) => (
          <CustomCardProduct key={producto.nombre} producto={producto} />
        ))}
      </div>
      <ReactPaginate
        previousLabel={"Anterior"}
        nextLabel={"Siguiente"}
        pageCount={Math.ceil(motorhome.length / itemsPerPage)}
        onPageChange={changePage}
        containerClassName={"pagination"}
        previousLinkClassName={"previous"}
        nextLinkClassName={"next"}
        disabledClassName={"disabled"}
        activeClassName={"active"}
      />
    </div>
  );
}

export default Recomendados;
