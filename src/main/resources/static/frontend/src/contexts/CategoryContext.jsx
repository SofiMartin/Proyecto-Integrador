// CategoryContext.js
import { createContext, useContext, useState } from "react";
import PropTypes from "prop-types";

const CategoryContext = createContext();

export const CategoryProvider = ({ children }) => {
  const [selectedCategoryIds, setSelectedCategoryIds] = useState(new Set());

  const updateSelectedCategory = (categoryId) => {
    setSelectedCategoryIds((prevSelectedIds) => {
      const newSelectedIds = new Set(prevSelectedIds);
      if (newSelectedIds.has(categoryId)) {
        newSelectedIds.delete(categoryId); // Si ya está seleccionado, quitarlo
      } else {
        newSelectedIds.add(categoryId); // Si no está seleccionado, agregarlo
      }
      return newSelectedIds;
    });
  };

  return (
    <CategoryContext.Provider
      value={{ selectedCategoryIds, updateSelectedCategory }}
    >
      {children}
    </CategoryContext.Provider>
  );
};

CategoryProvider.propTypes = {
  children: PropTypes.node.isRequired,
};

export const useCategory = () => {
  return useContext(CategoryContext);
};
