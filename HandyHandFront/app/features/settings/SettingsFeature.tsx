import React from "react";
import ContentPage from "../../containers/ContentPage";
import Alert from 'react-bootstrap/Alert';
import Button from "react-bootstrap/esm/Button";

export default function SettingsFeature() {
  const [show, setShow] = useState(true);
  return (
    <ContentPage>
         <>
      <Alert show={show} variant="success">
        <Alert.Heading>How's it going?!</Alert.Heading>
        <p>
          Ceci est un test!!!!!!!!!!!!!
        </p>
        <hr />
        <div className="d-flex justify-content-end">
          <Button onClick={() => setShow(false)} variant="outline-success">
            Close me y'all!
          </Button>
        </div>
      </Alert>

      {!show && <Button onClick={() => setShow(true)}>Show Alert</Button>}
    </>
    </ContentPage>
      );
    }
