import { useState } from "react";
import { Form } from "react-bootstrap";

// ---------- Como los usuarios van a elegir la categoria ------------

const CategorySelector = ({ categories, onSelectCategory }) => {
  const [selectedCategory, setSelectedCategory] = useState("");

  const handleSelect = (event) => {
    setSelectedCategory(event.target.value);
    onSelectCategory(event.target.value);
  };

  return (
    <Form>
      <Form.Group controlId="categorySelect">
        <Form.Label>Select a Category</Form.Label>
        <Form.Control
          as="select"
          value={selectedCategory}
          onChange={handleSelect}
        >
          <option value="">All Categories</option>
          {categories.map((category) => (
            <option key={category} value={category}>
              {category}
            </option>
          ))}
        </Form.Control>
      </Form.Group>
    </Form>
  );
};

export default CategorySelector;
