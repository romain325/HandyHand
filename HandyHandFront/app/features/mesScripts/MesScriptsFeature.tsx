import React from 'react';
import { Link } from 'react-router-dom';
import ContentPage from '../../containers/ContentPage';
import routes from '../../constants/routes.json';
import styles from './MesScriptsFeature.css';

export default function MesScripts() {
  return (
    <ContentPage>
      <div className={styles.backButton} data-tid="backButton">
        <Link to={routes.HOME}>
          <i className="fa fa-arrow-left fa-3x" />
        </Link>
      </div>
    </ContentPage>
  );
}
