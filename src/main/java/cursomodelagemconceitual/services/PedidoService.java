package cursomodelagemconceitual.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cursomodelagemconceitual.domain.ItemPedido;
import cursomodelagemconceitual.domain.PagamentoComBoleto;
import cursomodelagemconceitual.domain.Pedido;
import cursomodelagemconceitual.domain.enums.EstadoPagamento;
import cursomodelagemconceitual.repositories.ItemPedidoRepository;
import cursomodelagemconceitual.repositories.PagamentoRepository;
import cursomodelagemconceitual.repositories.PedidoRepository;
import cursomodelagemconceitual.services.excepitions.ObjectNotFoundExcepiton;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) { 
		 Optional<Pedido> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundExcepiton( 
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); 
		} 
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto)obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
			
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}

	}

