// - Routes instead of RouteConfig
// - RouterModule instead of provideRoutes
import {Routes, RouterModule} from "@angular/router";
import {ManagerComponent} from "./manager/manager.component";
import {ReporterComponent} from "./reporter/reporter.component";
import {LoginComponent} from "./login/login.component";

const routes: Routes = [
{ path: '',       component: LoginComponent },
{ path: 'login',  component: LoginComponent },
{path: 'reporter',    component: ReporterComponent },
{path: 'manager',     component: ManagerComponent },
{path: '*',           component: LoginComponent }
];

// - Updated Export
export const routing = RouterModule.forRoot(routes);
