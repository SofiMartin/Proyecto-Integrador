import {
  Card,
  CardImg,
  CardText,
  CardBody,
  CardTitle,
  CardSubtitle,
  // Button,
} from "reactstrap";
import PropTypes from "prop-types";
import "./CustomCard.css";

function CustomCard({ catg, onClick, isSelected }) {
  const handleClick = () => {
    console.log("Clic en CustomCard");
    onClick();
  };
  return (
    <Card
      style={{ width: "22%" }}
      onClick={handleClick}
      className={`CustomCard ${isSelected ? "selected" : ""}`}
    >
      <CardImg top src={"../images/" + catg.file} alt="Card image cap" />
      <CardBody>
        <CardTitle tag="h5">Motorhome</CardTitle>
        <CardSubtitle tag="h6" className="mb-2 text-muted">
          {catg.nombre}
        </CardSubtitle>
        <CardText>{catg.descripcion}</CardText>
        {/* <Button>Alquilar</Button> */}
      </CardBody>
    </Card>
  );
}

CustomCard.propTypes = {
  catg: PropTypes.object.isRequired,
  onClick: PropTypes.func.isRequired, // Validar que onClick sea una función requerida
  isSelected: PropTypes.bool.isRequired,
};

export default CustomCard;
