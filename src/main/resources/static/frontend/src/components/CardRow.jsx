import { Row, Col } from "reactstrap";
import CustomCard from "./CustomCard";

function CardRow1() {
  return (
    <Row>
      <Col>
        <CustomCard imageSrc={"/images/MH1-1.jpg"} />
      </Col>
      <Col>
        <CustomCard imageSrc={"/images/MH2-1.jpg"} />
      </Col>
      <Col>
        <CustomCard imageSrc={"/images/MH3-2.jpg"} />
      </Col>
    </Row>
  );
}

function CardRow2() {
  return (
    <Row>
      <Col>
        <CustomCard imageSrc={"/images/MH4-1.jpg"} />
      </Col>
      <Col>
        <CustomCard imageSrc={"/images/MH5-5.jpg"} />
      </Col>
      <Col>
        <CustomCard imageSrc={"/images/MH6-1.jpg"} />
      </Col>
    </Row>
  );
}
export { CardRow1, CardRow2 };
