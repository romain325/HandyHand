import React from 'react';
import { Link } from 'react-router-dom';
import routes from '../constants/routes.json';
import styles from './Home.css';

export default function Home(): JSX.Element {
  return (
    <div className={styles.container} data-tid="container">
      <h2>DEMO N°2</h2>
      <div><Link to={routes.COUNTER}>Mes scripts</Link></div>
      <div><Link to={routes.CONNEXION}>Connexion</Link></div>
      <div><Link to={routes.ENREGISTREMENT}>Enregistrement</Link></div>
      <div><Link to={routes.SCRIPT}>Scripts</Link></div>
      <div><Link to={routes.MY_SCRIPT}>Mes scripts</Link></div>
      <div><Link to={routes.ADD_SCRIPT}>Ajouter script</Link></div>
    </div>
  );
}
