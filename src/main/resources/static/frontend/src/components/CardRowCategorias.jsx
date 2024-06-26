// CardRowCategorias.js
import React from "react";
import axios from "axios";
import CustomCard from "./CustomCard";
import { useCategory } from "../contexts/CategoryContext";
import "./CardRowCategorias.css";

function CardRowCategorias() {
  const { updateSelectedCategory, selectedCategoryIds } = useCategory();
  const [categoria, setCategoria] = React.useState([]);

  React.useEffect(() => {
    axios
      .get("http://localhost:8080/categoria")
      .then((res) => {
        setCategoria(res.data);
      })
      .catch((err) => console.log(err));
  }, []);

  const handleCategoryClick = (categoryId) => {
    updateSelectedCategory(categoryId);
  };

  return (
    <div>
      <h2>Categorias</h2>
      <div className="CardRow">
        {categoria.map((catg) => (
          <CustomCard
            key={catg.id}
            catg={catg}
            onClick={() => handleCategoryClick(catg.id)}
            isSelected={selectedCategoryIds.has(catg.id)}
          />
        ))}
      </div>
    </div>
  );
}

export default CardRowCategorias;
