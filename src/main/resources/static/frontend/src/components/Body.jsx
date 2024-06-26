import "./Body.css";
import CardRowCategorias from "./CardRowCategorias";
import SearchBar from "./SearchBar";
import SeccionHome from "./SeccionHome";
import Recomendados from "./Recomendados";

function Body() {
  return (
    <div className="body">
      <div className="container">
        <SeccionHome />
      </div>
      <div className="container-fluid searchBar">
        <SearchBar />
      </div>
      <div></div>
      <div className="container">
        <CardRowCategorias />
        <Recomendados />
      </div>
    </div>
  );
}

export default Body;
