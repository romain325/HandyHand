import React from "react";
import { Form } from "react-bootstrap";
import ContentPage from "../../containers/ContentPage";


export default function AccueilFeature() {
  return (
    <ContentPage>
      <Form>
        <Form.Check
          type="switch"
          id="custom-switch"
          label="actived"
        />
      </Form>
    </ContentPage>
  );
 }
