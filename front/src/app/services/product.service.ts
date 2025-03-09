
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
interface Product {
  id: number;
  name: string;
  price: number;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private apiUrl = 'http://localhost:8088/api/products';

  constructor(private http: HttpClient) {}

  private username: string = 'juan';
  private password: string = 'abc123'; // Password encriptado

  getAuthHeaders(): HttpHeaders {
    const base64Credentials = btoa(`${this.username}:${this.password}`);
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Basic ${base64Credentials}`
    });
  }

  list(): Observable<Product[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<Product[]>(this.apiUrl, { headers });
  }
  
  save(product: Product): Observable<HttpResponse<void>> {
    const headers = this.getAuthHeaders();
    return this.http.post<void>(this.apiUrl, product, { headers, observe: 'response' });
  }
  
  update(product: Product): Observable<HttpResponse<void>> {
    const headers = this.getAuthHeaders();
    return this.http.put<void>(this.apiUrl, product, { headers, observe: 'response' });
  }
  
  delete(productId: number): Observable<HttpResponse<void>> {
    const headers = this.getAuthHeaders();
    return this.http.delete<void>(`${this.apiUrl}/${productId}`, { headers, observe: 'response' });
  }
  
  getProductId(productId: number): Observable<HttpResponse<void>> {
    const headers = this.getAuthHeaders();
    return this.http.get<void>(`${this.apiUrl}/${productId}`, { headers, observe: 'response'});
  }

  getProductId2(productId: number): Observable<HttpResponse<void>> {
     return this.http.get<void>(`${this.apiUrl}/${productId}`, { observe: 'response' }); 
    }

}
