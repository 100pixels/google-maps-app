package org.cenidet.cc.publictransit.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cenidet.cc.publictransit.dto.Stop;

public class Grafo {

	public static int INFINITO = 0xFFFF;
	private ArrayList<Stop> listaVertices;
	private double[][] matrizAdyacencia;
	private Stop[] vertices;
	private int numeroVertices;
	
	private CaminoMinimo caminoMinimo;
	
	public Grafo(ArrayList<Stop> vertices) throws Exception{
		setListaVertices(vertices);
		initMatrizAdyacencia();
		llenarMatrizAdyacencia();
		initArregloVertices();
		this.numeroVertices = 0;
		
		caminoMinimo = new CaminoMinimo(this);
	}
	
	public void setListaVertices(ArrayList<Stop> vertices) throws Exception {
		if((vertices == null) || (vertices.size() == 0)){						
			throw new Exception("El parámetro es una referencia nula o no contiene ningún elemento");
		}else{
			this.listaVertices = vertices;
		}		
	}

	public void setMatrizAdyacencia(double[][] matrizAdyacencia) {
		this.matrizAdyacencia = matrizAdyacencia;
	}
	
	private void initMatrizAdyacencia() throws Exception{
		if(validarListaVertices()){
			int numVertices = listaVertices.size();
			matrizAdyacencia = new double[numVertices][numVertices];			
				for (int x = 0; x < numVertices; x++) {
					for (int y = 0; y < numVertices; y++) {
						matrizAdyacencia[x][y] = INFINITO;
					}
				}
		}else{
			throw new Exception("El parámetro es una referencia nula o no contiene ningún elemento");
		}
	}
	
	private void initArregloVertices() throws Exception{
		if(validarListaVertices()){
			int numVertices = listaVertices.size();
			vertices = new Stop[numVertices];
		}else{
			throw new Exception("El parámetro es una referencia nula o no contiene ningún elemento");
		}
	}
	
	private boolean validarListaVertices(){
		boolean validar = true;
			if((listaVertices == null) || (listaVertices.size() == 0)){
				validar =  false;
			}
		return validar;
	}
				
	private void llenarMatrizAdyacencia() throws Exception{
		if(matrizAdyacencia == null){
			throw new Exception("El campo matrizAdyacencia no ha sido inicializado");
		}
		
		for (int x = 0; x < listaVertices.size(); x++) {		
			Stop pivote = listaVertices.get(x);
			for (int y = 0; y < listaVertices.size(); y++) {
				Stop parada = listaVertices.get(y);
				ArrayList<Stop> paradasAdyacentes = pivote.getParadasAdyacentes();
				for (int z = 0; z < paradasAdyacentes.size(); z++) {
					Stop paradaAdyacente = paradasAdyacentes.get(z);
					if(paradaAdyacente.equals(parada)){
						matrizAdyacencia[x][y] =  paradaAdyacente.getDistanceMts();
					}
				}
			}
		}		
	}
				
	public void imprimirMatriz() throws Exception{		
		llenarMatrizAdyacencia();		
		for (int x = 0; x < matrizAdyacencia.length; x++) {
			for (int y = 0; y < matrizAdyacencia.length; y++) {
				System.out.print(matrizAdyacencia[x][y]+"\t");
			}
			System.out.print("\n");
		}
	}
	
	public void imprimirListaDeVertices(){
		for (Stop parada: listaVertices) {
			System.out.println(parada);
		}
	}
	
	public int getNumeroDeVertices(){
		return listaVertices.size();
	}
	
	public double[][] getMatrizDeAdyacencia(){
		return matrizAdyacencia;
	}
	
	public ArrayList<Stop> getListaDeVertices(){
		return listaVertices;
	}
	
	public int getElementIndex(Stop parada){
		return listaVertices.indexOf(parada);
	}
	
	public Stop getElementAt(int indice){
		return listaVertices.get(indice);
	}
	
	public ArrayList<Stop> encontrarCamino(Stop origen, Stop destino){
		String mensaje="El parámetro es una referencia nula";
		Objects.requireNonNull(origen, mensaje);
		Objects.requireNonNull(destino, mensaje);
		return caminoMinimo.encontrarCamino(origen, destino);		
	}
	
	
	
	
}
