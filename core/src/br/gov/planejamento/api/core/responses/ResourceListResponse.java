package br.gov.planejamento.api.core.responses;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.database.DatabaseData;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.serializers.CSVSerializer;
import br.gov.planejamento.api.core.serializers.HTMLSerializer;
import br.gov.planejamento.api.core.serializers.JSONSerializer;
import br.gov.planejamento.api.core.serializers.XMLSerializer;
import br.gov.planejamento.api.core.utils.ReflectionUtils;

public class ResourceListResponse<T extends Resource> extends Response implements List<Resource> {

	private String name = "resource_list";
	private String description = "";
	private int totalFoundResources = 0;
	private ArrayList<Resource> resources = new ArrayList<Resource>();

	/**
	 * Construtor da ResourceListResponse
	 * @param totalFoundResources Número total de registros encontrados no banco para este Request, ignorando o limite de paginação
	 */
	private ResourceListResponse (int totalFoundResources){
		setName(name);
		setDescription(description);
		this.totalFoundResources = totalFoundResources;
	}
	
	/**
	 * Constrói uma ResourceListResponse a partir de um DatabaseData e de um tipo de Resource especificado
	 * @param data Dados recebidos do Banco de Dados
	 * @param resourceType Tipo de Resource a ser criado
	 * @return Uma instância de ResourceListResponse contendo uma lista de Resources do tipo especificado
	 * @throws ApiException 
	 */
	public static <T extends Resource> ResourceListResponse<T> factory(DatabaseData data, Class<? extends Resource> resourceType) throws ApiException {
		
		ResourceListResponse<T> response = new ResourceListResponse<T>(data.getCount());
		
		for(DataRow row : data) {
			try {
				response.add(resourceType.getConstructor(DataRow.class).newInstance(row));
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				throw new CoreException("Houve um erro ao criar os Resources a partir da DatabaseData", e);
			}
		}
		
		return response;
	}
	
	/**
	 * Obtém a quantidade total de registros encontrados no banco para este request, ignorando o limite de paginação
	 * @return Quantidade total de Resources
	 */
	public int getTotalFoundResources() {
		return totalFoundResources;
	}
	
	/**
	 * Obtém a Lista de Resources encontrados para este request
	 * @return Lista de Resources
	 */
	public ArrayList<Resource> getResourceList() {
		ArrayList<Resource> resourceList = new ArrayList<Resource>();
		resourceList.addAll(this.subList(0, this.size()-1));
		return resourceList;
	}
	
	@Override
	public ArrayList<Link> getLinks() throws ApiException {
		return ReflectionUtils.getLinks(this);
	}
	
	@Override
	public SelfLink getSelfLink() {
		return new SelfLink(RequestContext.getContext().getFullPath(), this.description);
	}
	
	@Override
	public String toJSON() throws ApiException {
		return JSONSerializer.fromResourceListResponse(this);		
	}

	@Override
	public String toCSV() throws ApiException {
		return CSVSerializer.fromResourceListResponse(this);
	}

	@Override
	public Object toHTML() throws ApiException {
		return HTMLSerializer.fromResourceListResponse(this);
	}
	
	@Override
	public String toXML() throws ApiException {
		return XMLSerializer.fromResourceListResponse(this);
	}

	@Override
	public int size() {
		return resources.size();
	}

	@Override
	public boolean isEmpty() {
		return resources.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return resources.contains(o);
	}

	@Override
	public Iterator<Resource> iterator() {
		return resources.iterator();
	}

	@Override
	public Object[] toArray() {
		return resources.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return resources.toArray(a);
	}

	@Override
	public boolean add(Resource e) {
		return resources.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return resources.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return resources.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Resource> c) {
		return resources.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Resource> c) {
		return resources.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return resources.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return resources.retainAll(c);
	}

	@Override
	public void clear() {
		resources.clear();
	}

	@Override
	public Resource get(int index) {
		return resources.get(index);
	}

	@Override
	public Resource set(int index, Resource element) {
		return resources.set(index, element);
	}

	@Override
	public void add(int index, Resource element) {
		resources.add(index, element);
	}

	@Override
	public Resource remove(int index) {
		return resources.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return resources.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return resources.lastIndexOf(o);
	}

	@Override
	public ListIterator<Resource> listIterator() {
		return resources.listIterator();
	}

	@Override
	public ListIterator<Resource> listIterator(int index) {
		return resources.listIterator(index);
	}

	@Override
	public List<Resource> subList(int fromIndex, int toIndex) {
		return resources.subList(fromIndex, toIndex);
	}
	
	@Override
	public int getHttpStatusCode() {
		return Constants.HttpStatusCodes.OK;
	}
}