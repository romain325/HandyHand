import React from 'react';
import { Link } from 'react-router-dom';
import routes from '../../constants/routes.json';

export default function ConnexionFeature() {
  return (
    <div>
      <div data-tid="backButton">
        <Link to={routes.HOME}>
          <i className="fa fa-arrow-left fa-3x" />
        </Link>
      </div>
      <h2>Test</h2>
    </div>
  );
}
