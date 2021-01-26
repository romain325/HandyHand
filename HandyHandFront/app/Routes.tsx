/* eslint react/jsx-props-no-spreading: off */
import React from 'react';
import { Switch, Route } from 'react-router-dom';
import * as routes from './constants/routes.json';
import App from './containers/App';
import HomeFeature from './features/home/HomeFeature';
import AddScriptFeature from './features/addScript/AddScriptFeature';
import ConnectionFeature from './features/connection/ConnectionFeature';
import RegisterFeature from './features/register/RegisterFeature';
import ScriptsFeatures from './features/scripts/ScriptsFeature';
import SettingsFeature from './features/settings/SettingsFeature';
import MyScriptsFeature from './features/myScripts/myScriptsFeature';

// Lazily load routes and code split with webpack
const LazyCounterPage = React.lazy(() =>
  import(/* webpackChunkName: "CounterPage" */ './containers/CounterPage')
);

const CounterPage = (props: Record<string, any>) => (
  <React.Suspense fallback={<h1>Loading...</h1>}>
    <LazyCounterPage {...props} />
  </React.Suspense>
);


export default function Routes() {
  return (
    <App>
      <Switch>
        <Route exact path={routes.HOME} component={HomeFeature} />
        <Route path={routes.CONNECTION} component={ConnectionFeature} />
        <Route path={routes.REGISTER} component={RegisterFeature} />
        <Route path={routes.SCRIPTS} component={ScriptsFeatures} />
        <Route path={routes.MY_SCRIPT} component={MyScriptsFeature} />
        <Route path={routes.ADD_SCRIPT} component={AddScriptFeature} />
        <Route path={routes.SETTINGS} component={SettingsFeature} />
      </Switch>
    </App>
  );

}
