import { useState, useEffect } from "react";
import { Button, Modal, ModalBody } from "reactstrap";
import PropTypes from "prop-types";
import SliderCardImage from "./SliderCardImage";
import axios from "axios";
import "./ImagenesModales.css";

function ImagenesModales(nombre) {
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);
  const [imagenes, setImagenes] = useState([]);
  useEffect(() => {
    axios
      .get(` http://localhost:8080/imagenes/buscarPorProducto? `, {
        params: { nombre: nombre.nombre },
      })
      .then((res) => {
        const imagenesSeparadas = res.data[0].file.split("-");
        setImagenes(imagenesSeparadas);
      })
      .catch((err) => console.log(err));
  }, []);
  const externalCloseBtn = (
    <button
      type="button"
      className="close"
      style={{ position: "absolute", top: "15px", right: "15px" }}
      onClick={toggle}
    >
      &times;
    </button>
  );
  return (
    <div>
      <Button
        onClick={toggle}
        outline
        size="md"
        className="botonImagen --bs-orange"
      >
        Ver Más
      </Button>
      <Modal
        size="xl"
        isOpen={modal}
        external={externalCloseBtn}
        toggle={toggle}
        centered
      >
        <ModalBody className="modalBody">
          <SliderCardImage src={Object.values(imagenes)} alt="Card image cap" />
        </ModalBody>
      </Modal>
    </div>
  );
}

ImagenesModales.propTypes = {
  className: PropTypes.string,
  nombre: PropTypes.string.isRequired,
};

export default ImagenesModales;
