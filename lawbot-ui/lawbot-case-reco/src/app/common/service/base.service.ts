import { HttpClient,HttpErrorResponse  } from '@angular/common/http';
import { Observable} from 'rxjs';
import { catchError ,map } from 'rxjs/operators';
import { BsModalService } from 'ngx-bootstrap/modal';
import { DialogComponent } from "../component/dialog.component";
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

/**
 * 
 */
export abstract class BaseService {
    bsModalRef: BsModalRef;
    constructor(private _http: HttpClient , private _modalService: BsModalService) { }

    post(url: string , body: any , options?: any): Observable<Object>{
        return this._http.post(url, body , options).pipe(
            catchError((err: any) => {
                this.openDialog({
                    title: 'Error',
                    message: "Please contact the provider"
                });
                return Observable.empty<Object>();
            }),
            map(res => {
                this.handleError(res);
                return res;
            })
        );
    }

    get(url , options?: any): Observable<Object>{
        return this._http.get(url , options).pipe(
            catchError((err: any) => {
                this.openDialog({
                    title: 'Error',
                    message: "Please contact the provider"
                });
                return Observable.empty<Object>();
            }),
            map(res => {
                if(url != "/account/me")
                    this.handleError(res);
                return res;
            })
        );
    }

    private handleError(res: any){
        let code = res.code;
        switch(code){
        case 200:
            return;
        case 401: 
            location.href = "/account/login?callback=" + location.href;
            break;
        default:
            this.openDialog({
                title: 'Error',
                message: res.message
            });
        }
    }

    private openDialog(initialState){
        alert(initialState.message);
        //this.bsModalRef = this._modalService.show(DialogComponent, {initialState});
    }

    
}