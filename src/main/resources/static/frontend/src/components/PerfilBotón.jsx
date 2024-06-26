import Cierre from "./Cierre"
import { useState, useEffect } from 'react';
import axios from "axios";
import {
  Dropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem, Modal, ModalHeader, ModalBody, ModalFooter
} from 'reactstrap';
import PropTypes from 'prop-types';

function PerfilBotón({ direction, inicialNombre, nombre, mail, ...args }) {
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [modal, setModal] = useState(false);

  const [usuario, setUsuario] = useState(localStorage.getItem("usuario"));
  const [email, setEmail] = useState(localStorage.getItem("email"));
  const [rol, setRol] = useState(localStorage.getItem("rol"));


 const toggle = () => setDropdownOpen((prevState) => !prevState);
 const toggleModal = () => setModal(!modal);

  return (
    <div>
      <Dropdown isOpen={dropdownOpen} toggle={toggle} direction={direction}>
        <DropdownToggle>{inicialNombre}</DropdownToggle>
        <DropdownMenu {...args}>
          <DropdownItem header>¡Hola, {nombre}!</DropdownItem>
          <DropdownItem onClick={toggleModal}>Mi perfil</DropdownItem>
          <DropdownItem >Reservas</DropdownItem>
          <DropdownItem divider />
          <DropdownItem><Cierre/></DropdownItem>
        </DropdownMenu>
      </Dropdown>
      <Modal isOpen={modal} toggle={toggleModal}>
        <ModalHeader toggle={toggleModal}>Perfil de Usuario</ModalHeader>
        <ModalBody>
        {usuario && (
            <>
         <p>Nombre: {nombre} </p> 
         <p>e-mail: {email} </p> 
         <p>Rol: {rol} </p> 
                   </>
          )}
        </ModalBody>
        <ModalFooter>
        
       </ModalFooter>
      </Modal>
    </div>
  );
}

PerfilBotón.propTypes = {
  direction: PropTypes.string,
  inicialNombre: PropTypes.string,
  nombre: PropTypes.string,
};

export default PerfilBotón;