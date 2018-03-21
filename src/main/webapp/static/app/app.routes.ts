// - Routes instead of RouteConfig
// - RouterModule instead of provideRoutes
import {Routes, RouterModule} from "@angular/router";
import {ManagerComponent} from "./manager/manager.component";
import {ReporterComponent} from "./reporter/reporter.component";
import {Login} from "./login/login";
import {AdminComponent} from "./admin/admin.component";

const routes: Routes = [
{path: '',            component: Login },
{path: 'authenticate',component: Login },
{path: 'reporter',    component: ReporterComponent },
{path: 'manager',     component: ManagerComponent },
{path: 'admin',       component: AdminComponent },
{path: '*',           component: ReporterComponent }
];

// - Updated Export
export const routing = RouterModule.forRoot(routes);
